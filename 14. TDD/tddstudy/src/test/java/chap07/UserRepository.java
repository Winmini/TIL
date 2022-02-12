package chap07;

public interface UserRepository {
	void save(User user);

	boolean checkDuplicatedId(String id);
}
