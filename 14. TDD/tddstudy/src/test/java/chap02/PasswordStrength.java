package chap02;

public enum PasswordStrength {
	STRONG("강함"), NORMAL("보통"), WEEK("약함"), INVALID("유효하지 않음");

	private final String passwordStrength;

	PasswordStrength(String strength) {
		this.passwordStrength = strength;
	}

	@Override
	public String toString() {
		return passwordStrength;
	}
}
