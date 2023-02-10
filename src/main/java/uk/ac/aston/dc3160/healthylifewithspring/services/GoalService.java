package uk.ac.aston.dc3160.healthylifewithspring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.aston.dc3160.healthylifewithspring.database.GoalRepository;
import uk.ac.aston.dc3160.healthylifewithspring.models.Goal;

@Service
public class GoalService {
	@Autowired
	GoalRepository repository;
	
	public List<Goal> getGoals(Integer userId) throws Exception {
		return repository.findByUserId(userId);
	}
	
	public List<Goal> setGoals(List<Goal> goalsToSet) throws Exception {
		return (List<Goal>) repository.saveAll(goalsToSet);
	}
	
	public void deleteGoal(int goalId) throws Exception {
		repository.deleteById(goalId);
	}

}
