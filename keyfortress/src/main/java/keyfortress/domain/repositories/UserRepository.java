package keyfortress.domain.repositories;

import java.util.List;

import keyfortress.domain.user.User;

public interface UserRepository {
	public User findUserByID(String userId);

	public void saveUser(User user);

	public List<User> findAll();

	public void update(User user);

	public void delete(String userId);
}
