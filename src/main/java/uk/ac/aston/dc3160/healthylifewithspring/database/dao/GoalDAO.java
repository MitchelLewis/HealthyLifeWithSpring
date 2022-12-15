package uk.ac.aston.dc3160.healthylifewithspring.database.dao;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.aston.dc3160.healthylifewithspring.models.Goal;
import uk.ac.aston.dc3160.healthylifewithspring.models.UserRecord;

@Repository
public class GoalDAO extends JdbcDaoSupport {
	@Autowired 
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	public List<Goal> getUserGoals(Integer userId) throws Exception {
		List<Goal> goals = new ArrayList<>();
		Connection conn = dataSource.getConnection();
		PreparedStatement sql = conn.prepareStatement("select * from user_goals where userId = ?");
		sql.setInt(1, userId);
		ResultSet rs = sql.executeQuery();
		while (rs.next()) {
			Goal goal = new Goal(rs.getString("goalName"), rs.getString("goalUnit"), rs.getInt("currentProgress"),
					rs.getInt("target"));
			goals.add(goal);
		}
		conn.close();
		sql.close();
		return goals;
	}
	
	//Transactional to bulk set goals to conform to ACID theory
	@Transactional
	public void setUserGoals(List<Goal> goalsToSet, int userId) throws Exception {
		Connection conn = dataSource.getConnection();
		PreparedStatement statement = conn.prepareStatement("insert into user_goals(goalName, goalUnit, currentProgress, target, userId) values (?, ?, ?, ?, ?)");
		for(Goal goal: goalsToSet) {
			statement.setString(1, goal.getGoalName());
			statement.setString(2, goal.getGoalUnit());
			statement.setInt(3, goal.getCurrentProgress());
			statement.setInt(4, goal.getTarget());
			statement.setInt(5, userId);
			statement.addBatch();
		}
		statement.executeBatch();
		statement.close();
		conn.close();
	}
	
	//Transactional to bulk update goals to conform to ACID theory
	@Transactional
	public void updateGoals(List<Goal> goalsToSet, int userId) throws Exception {
		Connection conn = dataSource.getConnection();
		PreparedStatement statement = conn.prepareStatement("update user_goals set target = ? WHERE userId = ? AND goalName = ?");
		for(Goal goal: goalsToSet) {
			statement.setInt(1, goal.getTarget());
			statement.setInt(2, userId);
			statement.setString(3, goal.getGoalName());
			statement.addBatch();
		}
		statement.executeBatch();
		statement.close();
		conn.close();
	}
	
	//Transactional to bulk update progress to conform to ACID theory
	@Transactional
	public void updateProgress(List<Goal> goalsToUpdate, int userId) throws Exception {
		Connection conn = dataSource.getConnection();
		PreparedStatement statement = conn.prepareStatement("update user_goals set currentProgress = ? WHERE userId = ? AND goalName = ?");
		for(Goal goal: goalsToUpdate) {
			statement.setInt(1, goal.getCurrentProgress());
			statement.setInt(2, userId);
			statement.setString(3, goal.getGoalName());
			statement.execute();
		}
		statement.executeBatch();
		conn.close();
	}
	
	public void deleteGoal(String goalName, int userId) throws Exception {
		Connection conn = dataSource.getConnection();
		PreparedStatement statement = conn.prepareStatement("delete from user_goals WHERE userId = ? AND goalName = ?");
		statement.setInt(1, userId);
		statement.setString(2, goalName);
		statement.execute();
		statement.close();
		conn.close();
	}

}
