package chap07;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {
	private Map<String, User> users = new HashMap<>();

	@Override
	public void save(User user) {
		users.put(user.getId(), user);
	}
}
