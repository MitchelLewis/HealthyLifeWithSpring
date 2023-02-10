package uk.ac.aston.dc3160.healthylifewithspring.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@SessionAttributes("userSession")
public class SignInController {
	Logger logger = LoggerFactory.getLogger(SignInController.class);

	@Autowired
	UserService userRecordService;

	@Autowired
	GoalService goalService;
	
	@RequestMapping(value = "/sign-in", method = RequestMethod.GET)
	public String signIn() {
		return "sign_in";
	}

	@RequestMapping(value = "/sign-in", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView handleSignIn(@RequestParam Map<String, String> formData, @ModelAttribute("userSession") UserSession userSession, Model model) {
		if (validateFormData(formData)) {
			Integer optUserId;
			try {
				optUserId = validateSignIn(formData.get("email"), formData.get("password"));
				if (optUserId != null) {
					String userName = getUserName(optUserId);
					ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
					userSession.setName(userName);
					userSession.setUser_id(optUserId);
					userSession.setGoals(getUserGoals(optUserId));
					return modelAndView;
				} else {
					List<String> errors = new ArrayList<>();
					errors.add("Your email/password is incorrect");
					ModelAndView modelAndView =  new ModelAndView("sign_in");
					modelAndView.addObject("errors", errors);
					return modelAndView;
				}
			} catch (Exception e) {
				logger.error("Exception occurred: ", e);
				ModelAndView modelAndView =  new ModelAndView("error");
				return modelAndView;
			}
		} else {
			List<String> invalidParameters = getInvalidParameters(formData);
			ModelAndView modelAndView =  new ModelAndView("sign_in");
			modelAndView.addObject("errors", invalidParameters);
			return modelAndView;
		}
	}
	
	@ModelAttribute(name = "userSession")
	public UserSession setUserInSession(UserSession userSession) {
		return new UserSession();
	}


	private boolean validateFormData(Map<String, String> formData) {
		String email = formData.get("email");
		String password = formData.get("password");
		if (email.length() == 0 || password.length() == 0) {
			return false;
		}

		return true;
		// TODO: add more form validation
	}

	private List<String> getInvalidParameters(Map<String, String> formData) {
		List<String> errors = new ArrayList<>();
		String email = formData.get("email");
		String password = formData.get("password");
		if (email.length() == 0) {
			errors.add("You must provide your email address");
		}
		if (password.length() == 0) {
			errors.add("You must provide your password");
		}
		return errors;
	}

	private Integer validateSignIn(String email, String password)
			throws Exception {
		Integer usersFound = userRecordService.getUserIdForEmailAndPassword(email, password);
		if(usersFound != null) {
			return usersFound.intValue();
		} else {
			return null;
		}
	}

	private String getUserName(Integer userId) throws Exception {
		String userName = userRecordService.getUserName(userId);
		return userName;
	}

	private List<Goal> getUserGoals(Integer userId) throws Exception {
		return goalService.getGoals(userId);
	}

}
