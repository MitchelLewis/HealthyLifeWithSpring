package uk.ac.aston.dc3160.healthylifewithspring.controllers;

import java.util.ArrayList;
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
	
}
