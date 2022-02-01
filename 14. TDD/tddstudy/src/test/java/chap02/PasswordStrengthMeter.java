package chap02;

import java.util.Objects;

public class PasswordStrengthMeter {

	private static final int PASSWORD_LENGTH_STANDARD = 8;
	private static final String CONTAINING_NUMBER_REGEX = "^[^0-9]*$";

	public PasswordStrength meter(String password) {
		if (password == null || password.isEmpty())
			return PasswordStrength.INVALID;
		if (meterPasswordLength(password) || meterContainingNumber(password))
			return PasswordStrength.NORMAL;
		return PasswordStrength.STRONG;
	}

	// private boolean

	private boolean meterPasswordLength(String password) {
		return password.length() < PASSWORD_LENGTH_STANDARD;
	}

	private boolean meterContainingNumber(String password) {
		return password.matches(CONTAINING_NUMBER_REGEX);
	}
}
