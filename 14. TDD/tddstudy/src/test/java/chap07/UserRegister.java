package chap07;

public class UserRegister {
	private final WeakPasswordChecker passwordChecker;
	private final UserRepository userRepository;

	public UserRegister(WeakPasswordChecker passwordChecker, MemoryUserRepository userRepository) {
		this.passwordChecker = passwordChecker;
		this.userRepository = userRepository;
	}

	public void register(String id, String pw, String email) {
		if (passwordChecker.checkPasswordWeak(pw)) {
			throw new IllegalArgumentException("[ERROR] 비밀번호가 약합니다.");
		}
		if (userRepository.checkDuplicatedId(id)) {
			throw new IllegalArgumentException("[ERROR] 중복된 아이디 입니다.");
		}

	}
}
