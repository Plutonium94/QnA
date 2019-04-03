package fr.unice.ui.mvc;

import fr.unice.ui.*;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequestMapping("/questions")
@Controller
public class QuestionController {

	@RequestMapping(value = "/new", method={RequestMethod.GET})
	public String showNewForm(Model model) {
		model.addAttribute("question", new Question());
		return "questions/new";
	}	

	@PostMapping("/new")
	public String newFormSubmit(@ModelAttribute Question question, BindingResult br, Model model) {
		model.addAttribute("question",question);
		return "questions/nice";
	}

}