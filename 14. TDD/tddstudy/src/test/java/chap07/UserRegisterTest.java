package chap07;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRegisterTest {
	private UserRegister userRegister;
	private final StubWeakPasswordChecker stubPasswordChecker = new StubWeakPasswordChecker();
	private final MemoryUserRepository fakeRepository = new MemoryUserRepository();

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(stubPasswordChecker, fakeRepository);
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
}
