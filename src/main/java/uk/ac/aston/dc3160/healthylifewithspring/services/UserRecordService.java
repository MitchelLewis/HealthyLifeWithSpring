package uk.ac.aston.dc3160.healthylifewithspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import uk.ac.aston.dc3160.healthylifewithspring.database.dao.UserRecordDAO;

@Service
@Component
public class UserRecordService {
	@Autowired
	UserRecordDAO userRecordDAO;
	
	public String getUserName(Integer userId) throws Exception {
		return userRecordDAO.getUserName(userId);
	}

	public Integer getCredentials(String email, String password) throws Exception {
		return userRecordDAO.getCredentials(email, password);
	}
	
	public String hashPassword(String password) throws Exception {
		return userRecordDAO.hashPassword(password);
	}
	
	public int createNewUser(String firstName, String lastName, String email, String password) throws Exception {
		String hashedPassword = hashPassword(password);
		return userRecordDAO.createNewUser(firstName, lastName, email, hashedPassword);
	}
	
	public boolean doesUserAlreadyExist(String email) throws Exception {
		return userRecordDAO.doesUserAlreadyExist(email);
	}
}
