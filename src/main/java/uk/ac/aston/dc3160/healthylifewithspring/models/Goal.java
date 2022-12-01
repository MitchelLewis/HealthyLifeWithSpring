package uk.ac.aston.dc3160.healthylifewithspring.models;

public class Goal {
	
	private String goalName;
	private String goalUnit;
	private int currentProgress;
	private int target;
	
	public Goal(String goalName, String goalUnit, int currentProgress, int target) {
		this.goalName = goalName;
		this.goalUnit = goalUnit;
		this.currentProgress = currentProgress;
		this.target = target;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public int getCurrentProgress() {
		return currentProgress;
	}

	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}

	public String getGoalUnit() {
		return goalUnit;
	}

	public String getGoalName() {
		return goalName;
	}
}
