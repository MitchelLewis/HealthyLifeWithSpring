package uk.ac.aston.dc3160.healthylifewithspring.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.aston.dc3160.healthylifewithspring.models.Goal;
import uk.ac.aston.dc3160.healthylifewithspring.models.UserSession;
import uk.ac.aston.dc3160.healthylifewithspring.services.GoalService;

@Controller
@SessionAttributes("userSession")
public class UpdateGoalsController {
	Logger logger = LoggerFactory.getLogger(SignInController.class);

	@Autowired
	GoalService goalService;
	
	@RequestMapping(value = {"/update-goals"}, method = RequestMethod.GET)
	public String updateGoals(@ModelAttribute("userSession") UserSession userSession) {
		if(userSession.getUser_id() == null) {
			return "redirect:/sign-in";
		} else {
			return "update_goals.jspx";
		}
	}
	
	@ModelAttribute(name = "userSession")
	public UserSession setUserInSession(UserSession userSession) {
		return new UserSession();
	}
	
	@RequestMapping(value = {"/update-goals"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView handleUpdateGoals(@RequestParam Map<String, String> formData, @ModelAttribute("userSession") UserSession userSession, Model model) {
		List<Goal> goals = userSession.getGoals();
		List<Goal> enteredGoals = new ArrayList<>();
		for(Goal goal: goals) {
			try {
				String target = formData.get(goal.getGoalName() + ".target");
				enteredGoals.remove(goal);
				goal.setTarget(Integer.valueOf(target));
				enteredGoals.add(goal);
			} catch(NullPointerException | NumberFormatException e) {
				enteredGoals.add(goal);
				continue;
			}
		}
		try {
			goalService.updateGoals(goals, userSession);
		} catch (Exception e) {
			logger.error("Exception occurred: ", e);
			ModelAndView modelAndView =  new ModelAndView("error.jsp");
			return modelAndView;
		}
		userSession.setGoals(enteredGoals);
		ModelAndView modelAndView =  new ModelAndView("redirect:/dashboard");
		return modelAndView;
	}
	
	@RequestMapping(value = {"/goal"}, method = RequestMethod.DELETE)
    protected ResponseEntity<String> deleteGoals(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userSession") UserSession userSession, @RequestParam String goalName) throws ServletException, IOException {
		List<Goal> goals = userSession.getGoals();
        boolean isSuccess = false;
        
        for(Goal goal: goals) {
            if(goal.getGoalName().equals(goalName)) {
                try {
                    goalService.deleteGoal(goalName, userSession);
                    goals.remove(goal);
                    isSuccess = true;
                    break;
                } catch(Exception e) {
                	logger.error("Exception occurred: ", e);
                }
            }
        }
        userSession.setGoals(goals);
        if(isSuccess) return ResponseEntity.ok().build(); 
        else return ResponseEntity.notFound().build();
    }
	
}
