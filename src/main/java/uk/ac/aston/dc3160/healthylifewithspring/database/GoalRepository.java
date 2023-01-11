package uk.ac.aston.dc3160.healthylifewithspring.database;

import uk.ac.aston.dc3160.healthylifewithspring.models.Goal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal, Integer> {
	List<Goal> findByUserId(int userId);
}
