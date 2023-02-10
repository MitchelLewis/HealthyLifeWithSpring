package uk.ac.aston.dc3160.healthylifewithspring.database;

import uk.ac.aston.dc3160.healthylifewithspring.models.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	List<User> findByEmail(String email);
	
	List<User> findByEmailAndPasswordHash(String email, String passwordHash);
}
