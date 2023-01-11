package uk.ac.aston.dc3160.healthylifewithspring.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
public class UpdateProgressController {
	Logger logger = LoggerFactory.getLogger(SignInController.class);

	@Autowired
	GoalService goalService;
	
	@RequestMapping(value = {"/update-progress"}, method = RequestMethod.GET)
	public String updateProgress(@ModelAttribute("userSession") UserSession userSession) {
		if(userSession.getUser_id() == null) {
			return "redirect:/sign-in";
		} else {
			return "update_progress";
		}
	}
	
	@ModelAttribute(name = "userSession")
	public UserSession setUserInSession(UserSession userSession) {
		return new UserSession();
	}
	
	@RequestMapping(value = {"/update-progress"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView handleUpdateProgress(@RequestParam Map<String, String> formData, @ModelAttribute("userSession") UserSession userSession, Model model) {
		List<Goal> goals = userSession.getGoals();
		String goalName = formData.get("goal");
		String goalUpdate = formData.get("goal-update");
		for(Goal goal: goals) {
			try {
				if(goal.getGoalName().equals(goalName)) {
					goals.remove(goal);
					Integer updatedProgress = goal.getCurrentProgress() + Integer.valueOf(goalUpdate);
					goal.setCurrentProgress(updatedProgress);
					goals.add(goal);
					break;
				}
			} catch(NullPointerException e) {
				continue;
			}
		}
		try {
			goalService.setGoals(goals);
		} catch (Exception e) {
			logger.error("Exception occurred: ", e);
			ModelAndView modelAndView =  new ModelAndView("error");
			return modelAndView;
		}
		userSession.setGoals(goals);
		ModelAndView modelAndView =  new ModelAndView("redirect:/dashboard");
		return modelAndView;
	}
	
}
