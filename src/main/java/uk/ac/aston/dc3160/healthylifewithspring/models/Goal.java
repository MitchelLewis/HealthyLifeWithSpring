package uk.ac.aston.dc3160.healthylifewithspring.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user_goals")
public class Goal implements Serializable {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "goalName", nullable = false)
	private String goalName;
	@Column(name = "goalUnit", nullable = false)
	private String goalUnit;
	@Column(name = "currentProgress", nullable = false)
	private int currentProgress;
	@Column(name = "target", nullable = false)
	private int target;
	@Column(name = "userId", nullable = false)
	private int userId;
	
	public Goal(String goalName, String goalUnit, int currentProgress, int target, int userId) {
		this.goalName = goalName;
		this.goalUnit = goalUnit;
		this.currentProgress = currentProgress;
		this.target = target;
		this.userId = userId;
	}
	
	public Goal() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public String getGoalUnit() {
		return goalUnit;
	}

	public void setGoalUnit(String goalUnit) {
		this.goalUnit = goalUnit;
	}

	public int getCurrentProgress() {
		return currentProgress;
	}

	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
