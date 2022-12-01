package uk.ac.aston.dc3160.healthylifewithspring.models;

import java.util.List;

public class UserSession {
	private String name;
	private Integer user_id;
	private List<Goal> goals;
	private List<String> achievements;
	
	public void setName(String name) {
		this.name = name;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public void setAchievements(List<String> achievements) {
		this.achievements = achievements;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	
	public List<Goal> getGoals() {
		return goals;
	}

	public List<String> getAchievements() {
		return achievements;
	}
}
