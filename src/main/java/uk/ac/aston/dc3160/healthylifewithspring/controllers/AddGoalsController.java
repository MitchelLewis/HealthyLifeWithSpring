package uk.ac.aston.dc3160.healthylifewithspring.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
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
import uk.ac.aston.dc3160.healthylifewithspring.services.UserService;

@Controller
@SessionAttributes("userSession")
public class AddGoalsController {
	Logger logger = LoggerFactory.getLogger(SignInController.class);
	
	private HashMap<String, String> allGoals;

	@Autowired
	UserService userRecordService;

	@Autowired
	GoalService goalService;
	
	@PostConstruct
	public void initialize() {
        this.allGoals = new HashMap<>();
        this.allGoals.put("Calories", "kcals");
        this.allGoals.put("Protein", "grams");
        this.allGoals.put("Salt", "grams");
        this.allGoals.put("Sugar", "grams");
        this.allGoals.put("Exercise", "minutes");
        this.allGoals.put("Working hours", "hours");
        this.allGoals.put("Sleep", "hours");
	}
	
	@RequestMapping(value = {"/add-goals"}, method = RequestMethod.GET)
	public String addGoals(@ModelAttribute("userSession") UserSession userSession) {
		if(userSession.getUser_id() == null) {
			return "redirect:/sign-up";
		} else {
			return "sign_up_success";
		}
	}
	
	@ModelAttribute(name = "userSession")
	public UserSession setUserInSession(UserSession userSession) {
		return new UserSession();
	}
	
	@RequestMapping(value = {"/add-goals"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView handleAddGoals(@RequestParam Map<String, String> formData, @ModelAttribute("userSession") UserSession userSession, Model model) {
		userSession.setGoals(new ArrayList<Goal>());
		List<Goal> enteredGoals = new ArrayList<>();
		int userId = userSession.getUser_id();
		for(String goal: allGoals.keySet()) {
			try {
				String goalTargetAsString = formData.get(goal + ".target");
				int target = Integer.valueOf(goalTargetAsString);
				Goal goalRecord = new Goal(goal, allGoals.get(goal), 0, target, userId);
				enteredGoals.add(goalRecord);
			} catch(NullPointerException | NumberFormatException e) {
				continue;
			}
		}
		try {
			goalService.setGoals(enteredGoals);
		} catch (Exception e) {
			logger.error("Exception occurred: ", e);
			ModelAndView modelAndView =  new ModelAndView("error");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
		userSession.setGoals(enteredGoals);
		return modelAndView;
	}
	
}
