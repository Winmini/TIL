package chap02;

public class PasswordStrengthMeter {

	private static final int PASSWORD_LENGTH_STANDARD = 8;
	private static final String CONTAINING_NUMBER_REGEX = ".*[0-9].*";

	public PasswordStrength meter(String password) {
		if (password == null || password.isEmpty()) {
			return PasswordStrength.INVALID;
		}

		int passwordStrength = meterPasswordStrength(password);
		return evaluate(passwordStrength);
	}

	private int meterPasswordStrength(String password) {
		int passwordStrength = 0;

		if (isEnoughLength(password)) {
			passwordStrength += 1;
		}
		if (hasNumber(password)) {
			passwordStrength += 1;
		}
		if (!isLowercase(password)) {
			passwordStrength += 1;
		}

		return passwordStrength;
	}

	private boolean isEnoughLength(String password) {
		return password.length() >= PASSWORD_LENGTH_STANDARD;
	}

	private boolean hasNumber(String password) {
		return password.matches(CONTAINING_NUMBER_REGEX);
	}

	private boolean isLowercase(String password) {
		return password.toLowerCase().equals(password);
	}

	private PasswordStrength evaluate(int passwordStrength) {
		if (passwordStrength <= 1) {
			return PasswordStrength.WEEK;
		}
		if (passwordStrength == 2) {
			return PasswordStrength.NORMAL;
		}
		return PasswordStrength.STRONG;
	}
}
