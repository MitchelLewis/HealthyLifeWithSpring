package uk.ac.aston.dc3160.healthylifewithspring.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name = "users")
public class User implements Serializable { 
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "firstName", nullable = false)
	private String firstName;
	@Column(name = "lastName", nullable = false)
	private String lastName;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "passwordHash", nullable = false)
	private String passwordHash;
	
	public User(String firstName, String surname, String email, String passwordHash) {
		this.firstName = firstName;
		this.lastName = surname;
		this.email = email;
		this.passwordHash = passwordHash;
	}
	
	public User() {
	
	}

	public String getfName() {
		return firstName;
	}

	public void setfName(String fName) {
		this.firstName = fName;
	}

	public String getlName() {
		return lastName;
	}

	public void setlName(String lName) {
		this.lastName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}