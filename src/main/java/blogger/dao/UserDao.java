package blogger.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import blogger.entity.User;

public interface UserDao extends JpaRepository<User, Long> {
	
	
}
