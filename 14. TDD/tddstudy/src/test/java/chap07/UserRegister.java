package chap07;

public class UserRegister {
	private final WeakPasswordChecker passwordChecker;
	private final MemoryUserRepository userRepository;

	public UserRegister(WeakPasswordChecker passwordChecker, MemoryUserRepository userRepository) {
		this.passwordChecker = passwordChecker;
		this.userRepository = userRepository;
	}

	public void register(String id, String pw, String email) {
		if (passwordChecker.checkPasswordWeak(pw)) {
			throw new IllegalArgumentException("[ERROR] 비밀번호가 약합니다.");
		}
	}
}
