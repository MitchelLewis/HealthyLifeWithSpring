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

import uk.ac.aston.dc3160.healthylifewithspring.models.UserSession;
import uk.ac.aston.dc3160.healthylifewithspring.services.UserService;

@Controller
@SessionAttributes("userSession")
public class SignUpController {
	Logger logger = LoggerFactory.getLogger(SignUpController.class);
	
	@Autowired
	UserService userRecordService;

	@RequestMapping(value = "/sign-up", method = RequestMethod.GET)
	public String signUp() {
		return "sign_up";
	}
	
	@RequestMapping(value = "/sign-up", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView handleSignUp(@RequestParam Map<String, String> formData, @ModelAttribute("userSession") UserSession userSession, Model model) {
		try {
            if (validateFormData(formData)) {
				ModelAndView modelAndView = new ModelAndView("redirect:/add-goals");
                createUserRecord(formData, userSession);
                return modelAndView;
            } else {
                List<String> invalidParameters = getInvalidParameters(formData);
				ModelAndView modelAndView = new ModelAndView("sign_up");
				modelAndView.addObject("errors", invalidParameters);
				return modelAndView;
            }
        } catch (Exception e) {
			logger.error("Exception occurred: ", e);
			ModelAndView modelAndView = new ModelAndView("error");
			return modelAndView;
        }
    }
	
	@ModelAttribute(name = "userSession")
	public UserSession setUserInSession(UserSession userSession) {
		return new UserSession();
	}

    private boolean validateFormData(Map<String, String> formData) throws Exception {
        String fName = formData.get("first-name");
        String lName = formData.get("surname");
        String email = formData.get("email");
        String password = formData.get("password");
        if (fName.length() == 0 || lName.length() == 0 || email.length() == 0 || password.length() == 0) {
            return false;
        }
        if(userRecordService.doesUserAlreadyExist(email)) {
        	return false;
        }
        return true;
        // TODO: add more form validation
    }

    private List<String> getInvalidParameters(Map<String, String> formData) throws Exception {
        List<String> errors = new ArrayList<>();
        String fName = formData.get("first-name");
        String lName = formData.get("surname");
        String email = formData.get("email");
        String password = formData.get("password");
        if (fName.length() == 0) {
            errors.add("You must provide your first name");
        }
        if (lName.length() == 0) {
            errors.add("You must provide your surname");
        }
        if (email.length() == 0) {
            errors.add("You must provide your email address");
        }
        if (password.length() == 0) {
            errors.add("You must provide your password");
        }
        if(userRecordService.doesUserAlreadyExist(email)) {
            errors.add("This email address is already registered to an account");
        }
        return errors;
    }

    private void createUserRecord(Map<String, String> formData, UserSession userSession)
            throws Exception {
        String fName = formData.get("first-name");
        String lName = formData.get("surname");
        String email = formData.get("email");
        String password = formData.get("password");
        int userId = (int) userRecordService.createNewUser(fName, lName, email, password);
        userSession.setUser_id(userId);
        userSession.setName(fName);
    }
}
