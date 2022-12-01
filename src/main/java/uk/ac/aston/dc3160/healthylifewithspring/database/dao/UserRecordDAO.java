package uk.ac.aston.dc3160.healthylifewithspring.database.dao;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import uk.ac.aston.dc3160.healthylifewithspring.models.UserRecord;

@Repository
public class UserRecordDAO extends JdbcDaoSupport {
	@Autowired 
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	public String getUserName(Integer userId) throws Exception {
		Connection conn = dataSource.getConnection();
		PreparedStatement sql = conn.prepareStatement("select firstName from users where id = ?");
		sql.setInt(1, userId);
		ResultSet rs = sql.executeQuery();
		try {
			if (!rs.next()) {
				throw new Exception("Could not find name for user with ID = " + userId);
			} else {
				return rs.getString("firstName");
			}
		} finally {
			sql.close();
			conn.close();
		}

	}
	
	public Integer getCredentials(String email, String password) throws Exception {
		Connection conn = dataSource.getConnection();
		PreparedStatement statement = conn.prepareStatement("select id, passwordHash from users where email = ?");
		statement.setString(1, email);
		ResultSet rs = statement.executeQuery();
		try {
			if (!rs.next()) {
				return null;
			} else {
				String hashedPassword = hashPassword(password);
				if (rs.getString("passwordHash").equals(hashedPassword)) {
					return rs.getInt("id");
				} else {
					return null;
				}
			}
		} finally {
			statement.close();
			conn.close();
		}
	}
	
	public String hashPassword(String plainTextPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(plainTextPassword.getBytes("utf8"));
        String hash = String.format("%040x", new BigInteger(1, md.digest()));
        return hash;	
	}
	
	public int createNewUser(String firstName, String lastName, String email, String passwordHash) throws Exception {
		Connection conn = dataSource.getConnection();
        PreparedStatement statement = conn.prepareStatement(
                "insert into users(firstName, lastName, email, passwordHash) values (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
		try {
	        statement.setString(1, firstName);
	        statement.setString(2, lastName);
	        statement.setString(3, email);
	        statement.setString(4, passwordHash);
	        statement.executeUpdate();
	        ResultSet generatedKeys = statement.getGeneratedKeys();
	        generatedKeys.next();
	        return generatedKeys.getInt(1);
		} finally {
	        statement.close();
	        conn.close();
		}

	}
	
	public boolean doesUserAlreadyExist(String email) throws Exception {
		Connection conn = dataSource.getConnection();
        PreparedStatement statement = conn.prepareStatement("select count(id) from users where email = ?");
        statement.setString(1, email);
        ResultSet existingEmailQueryResult = statement.executeQuery();
        existingEmailQueryResult.next();
        boolean doesUserAlreadyExist = existingEmailQueryResult.getInt(1) > 0;
        statement.close();
        conn.close();
        return doesUserAlreadyExist;
	}
}
