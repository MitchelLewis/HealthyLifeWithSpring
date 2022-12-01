package uk.ac.aston.dc3160.healthylifewithspring.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignOutController {

	@RequestMapping(value = "/sign-out", method = RequestMethod.GET)
	public String cookies(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
}
