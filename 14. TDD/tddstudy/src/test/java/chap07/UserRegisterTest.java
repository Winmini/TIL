package chap07;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class UserRegisterTest {
	private UserRegister userRegister;
	private final WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
	private final MemoryUserRepository fakeRepository = new MemoryUserRepository();
	private final EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(mockPasswordChecker, fakeRepository, mockEmailNotifier);
	}

	@DisplayName("약한 암호면 가입 실패")
	@Test
	void weakPassword() {
		BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pw")).willReturn(true);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			userRegister.register("id", "pw", "email");
		});
		assertTrue(thrown.getMessage().contains("[ERROR]"));
	}

	@DisplayName("중복된 ID면 가입 실패")
	@Test
	void duplicatedId() {
		fakeRepository.save(new User.Builder("id", "pw", "email").build());
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			userRegister.register("id", "pw", "email");
		});
		assertTrue(thrown.getMessage().contains("[ERROR]"));
	}

	@DisplayName("같은 ID가 없으면 가입 성공")
	@Test
	void successRegister() {
		userRegister.register("id", "pw", "email");
		assertTrue(fakeRepository.checkDuplicatedId("id"));
	}

	@DisplayName("가입하면 메일을 전송함")
	@Test
	void sendMailWhenRegister() {
		userRegister.register("id", "pw", "email");

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		BDDMockito.then(mockEmailNotifier).should().sendResisterEmail(captor.capture());

		String realEmail = captor.getValue();
		assertEquals("email", realEmail);
	}

	@Test
	void mockTest() {
		SpyEmailNotifier spyEmailNotifier = Mockito.mock(SpyEmailNotifier.class);
	}
}
