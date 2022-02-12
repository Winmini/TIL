package chap07;

public class User {
	private final String id;
	private final String password;
	private final String email;

	public static class Builder {
		private final String id;
		private final String password;
		private final String email;

		public Builder(String id, String password, String email) {
			this.id = id;
			this.password = password;
			this.email = email;
		}

		public User build() {
			return new User(this);
		}
	}

	private User(Builder builder) {
		id = builder.id;
		password = builder.password;
		email = builder.email;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ID: " + id;
	}
}
