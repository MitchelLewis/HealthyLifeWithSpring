package uk.ac.aston.dc3160.healthylifewithspring.models;

import java.util.Map;

public record UserRecord(String firstName, String surname, String email, String password) {
	public static UserRecord createRecord(Map<String, String[]> formData) {
		String fName = formData.get("first-name")[0];
		String lName = formData.get("surname")[0];
		String email = formData.get("email")[0];
		String password = formData.get("password")[0];
		return new UserRecord(fName, lName, email, password);
	}
}