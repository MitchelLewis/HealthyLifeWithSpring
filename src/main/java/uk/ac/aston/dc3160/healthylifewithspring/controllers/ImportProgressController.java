package uk.ac.aston.dc3160.healthylifewithspring.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.aston.dc3160.healthylifewithspring.models.Goal;
import uk.ac.aston.dc3160.healthylifewithspring.models.UserSession;
import uk.ac.aston.dc3160.healthylifewithspring.services.GoalService;

@Controller
@SessionAttributes("userSession")
public class ImportProgressController {
	Logger logger = LoggerFactory.getLogger(ImportProgressController.class);

	@Autowired
	GoalService goalService;

	@RequestMapping(value = "/import-progress", method = RequestMethod.GET)
	public String onPageLoad(@ModelAttribute UserSession userSession) {
		if (userSession.getUser_id() == null) {
			return "redirect:/sign-in";
		} else {
			return "import_progress";
		}
	}

	@RequestMapping(value = {
			"/import-progress" }, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ModelAndView handleFileUpload(@RequestParam("formFile") MultipartFile file,
			@ModelAttribute("userSession") UserSession userSession, Model model) {
		if (file.isEmpty()) {
			List<String> errors = new ArrayList<>();
			errors.add("Upload a non-empty file");
			ModelAndView modelAndView = new ModelAndView("import_progress");
			modelAndView.addObject("errors", errors);
			return modelAndView;
		}
		if (!file.getContentType().equals("text/csv")) {
			List<String> errors = new ArrayList<>();
			errors.add("Upload a .csv file");
			ModelAndView modelAndView = new ModelAndView("import_progress");
			modelAndView.addObject("errors", errors);
			return modelAndView;
		}
		InputStream fileContentsIS;
		try {
			fileContentsIS = file.getInputStream();
		} catch (IOException e) {
			logger.error("An exception occurred: " + e.getMessage());
			List<String> errors = new ArrayList<>();
			errors.add("There was an issue reading your file, please try again");
			ModelAndView modelAndView = new ModelAndView("import_progress");
			modelAndView.addObject("errors", errors);
			return modelAndView;
		}
		List<Goal> goals = userSession.getGoals();
		List<String> goalsUpdated = new ArrayList<>();
		Scanner s = new Scanner(fileContentsIS).useDelimiter("\\A");
		String fileContents = s.hasNext() ? s.next() : "";
		String[] lines = fileContents.split("\n");
		for(String line: lines) {
			String[] goalNameAndProgress = line.split(",");
			for(Goal goal: goals) {
				try {
					if(goal.getGoalName().equals(goalNameAndProgress[0])) {
						goals.remove(goal);
						Integer updatedProgress = goal.getCurrentProgress() + Integer.valueOf(goalNameAndProgress[1].replace("\n", "").replace("\r", ""));
						goal.setCurrentProgress(updatedProgress);
						goals.add(goal);
						goalsUpdated.add(goalNameAndProgress[0]);
						break;
					}
				} catch(NullPointerException e) {
					continue;
				}
			}
		}
		try {
			goalService.setGoals(goals);
			s.close();
			fileContentsIS.close();
		} catch (Exception e) {
			logger.error("Exception occurred: ", e);
			ModelAndView modelAndView = new ModelAndView("error");
			return modelAndView;
		}
		userSession.setGoals(goals);
		ModelAndView modelAndView = new ModelAndView("import_progress_success");
		modelAndView.addObject("goalsUpdated", goalsUpdated);
		return modelAndView;
	}

	@ModelAttribute(name = "userSession")
	public UserSession setUserInSession(UserSession userSession) {
		return new UserSession();
	}
}
