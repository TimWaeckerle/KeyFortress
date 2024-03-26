package user;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
	public User findUserByID(UUID userId);

	public void saveUser(User user);

	public List<User> findAll();

	public void update(User user);

	public void delete(String userId);

	public User findUserByUsername(String name);
}
