package keyfortress.domain.repositories;

import keyfortress.domain.entities.User;

public interface UserRepository {
	public User findUserByName(String username);

	public void saveUser(User user);
}
