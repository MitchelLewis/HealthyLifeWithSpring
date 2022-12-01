package uk.ac.aston.dc3160.healthylifewithspring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.aston.dc3160.healthylifewithspring.database.dao.GoalDAO;
import uk.ac.aston.dc3160.healthylifewithspring.models.Goal;
import uk.ac.aston.dc3160.healthylifewithspring.models.UserSession;

@Service
public class GoalService {
	@Autowired
	GoalDAO goalDAO;
	
	public List<Goal> getGoals(Integer userId) throws Exception {
		return goalDAO.getUserGoals(userId);
	}
	
	public void setGoals(List<Goal> goalsToSet, UserSession userSession) throws Exception {
		int userId = userSession.getUser_id();
		goalDAO.setUserGoals(goalsToSet, userId);
	}
	
	public void updateGoals(List<Goal> goalsToSet, UserSession userSession) throws Exception {
		int userId = userSession.getUser_id();
		goalDAO.updateGoals(goalsToSet, userId);
	}
	
	public void updateProgress(List<Goal> goalsToUpdate, UserSession userSession) throws Exception {
		int userId = userSession.getUser_id();
		goalDAO.updateProgress(goalsToUpdate, userId);
	}
}
