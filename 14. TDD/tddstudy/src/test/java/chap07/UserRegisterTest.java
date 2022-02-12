package chap07;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRegisterTest {
	private UserRegister userRegister;
	private final StubWeakPasswordChecker stubPasswordChecker = new StubWeakPasswordChecker();
	private final MemoryUserRepository fakeRepository = new MemoryUserRepository();
	private final SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(stubPasswordChecker, fakeRepository, spyEmailNotifier);
	}

	@DisplayName("약한 암호면 가입 실패")
	@Test
	void weakPassword() {
		stubPasswordChecker.setWeak(true);
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
		assertTrue(spyEmailNotifier.isCalled());
		assertEquals("email", spyEmailNotifier.getEmail());
	}
}
