package chap02;

public class PasswordStrengthMeter {

	private static final int PASSWORD_LENGTH_STANDARD = 8;
	private static final String CONTAINING_NUMBER_REGEX = ".*[0-9].*";

	public PasswordStrength meter(String password) {
		if (password == null || password.isEmpty())
			return PasswordStrength.INVALID;

		if (isEnoughLength(password) && !hasNumber(password) && isLowercase(password))
			return PasswordStrength.WEEK;

		if (!isEnoughLength(password) && hasNumber(password) && isLowercase(password))
			return PasswordStrength.WEEK;

		if (!isEnoughLength(password) || !hasNumber(password) || isLowercase(password))
			return PasswordStrength.NORMAL;
		return PasswordStrength.STRONG;
	}

	// private boolean

	private boolean isEnoughLength(String password) {
		return password.length() >= PASSWORD_LENGTH_STANDARD;
	}

	private boolean hasNumber(String password) {
		return password.matches(CONTAINING_NUMBER_REGEX);
	}

	private boolean isLowercase(String password) {
		return password.toLowerCase().equals(password);
	}
}
