package uk.ac.aston.dc3160.healthylifewithspring.services;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import uk.ac.aston.dc3160.healthylifewithspring.database.UserRepository;
import uk.ac.aston.dc3160.healthylifewithspring.models.User;

@Service
@Component
public class UserService {
	@Autowired
	UserRepository repository;
	
	private String hashPassword(String plainTextPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(plainTextPassword.getBytes("utf8"));
        String hash = String.format("%040x", new BigInteger(1, md.digest()));
        return hash;	
	}
	
	public String getUserName(Integer userId) throws Exception {
		return repository.findById(userId).get().getfName();
	}

	public Integer getUserIdForEmailAndPassword(String email, String password) throws Exception {
		String hashedPassword = hashPassword(password);
		List<User> usersFound = repository.findByEmailAndPasswordHash(email, hashedPassword);
		if(usersFound.size() == 1) {
			return usersFound.get(0).getId();
		} else {
			return null;
		}
	}
	
	public long createNewUser(String firstName, String lastName, String email, String password) throws Exception {
		String hashedPassword = hashPassword(password);
		User user = new User(firstName, lastName, email, hashedPassword);
		return repository.save(user).getId();
	}
	
	public boolean doesUserAlreadyExist(String email) throws Exception {
		return repository.findByEmail(email).size() == 1;
	}
}
