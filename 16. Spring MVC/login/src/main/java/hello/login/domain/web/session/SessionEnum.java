package hello.login.domain.web.session;

public enum SessionEnum {
	LOGIN_MEMBER("loginMember");

	private final String message;

	SessionEnum(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
