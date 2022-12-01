package uk.ac.aston.dc3160.healthylifewithspring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import uk.ac.aston.dc3160.healthylifewithspring.models.UserSession;

@Controller
@SessionAttributes("userSession")
public class DashboardController {
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(@ModelAttribute UserSession userSession) {
		if(userSession.getUser_id() == null) {
			return "redirect:/sign-in";
		} else {
			return "dashboard.jspx";
		}
	}
	
	@ModelAttribute(name = "userSession")
	public UserSession setUserInSession(UserSession userSession) {
		return new UserSession();
	}
	
}