package fr.unice.qna.ui.mvc;

import fr.unice.qna.persistence.*;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequestMapping("/questions")
@Controller
public class QuestionController {

	@Autowired
	private QuestionRepository questRep;

	@RequestMapping(value = "/new", method={RequestMethod.GET})
	public String showNewForm(Model model) {
		model.addAttribute("question", new Question());
		return "questions/new";
	}	

	@PostMapping("/new")
	public String newFormSubmit(@ModelAttribute Question question, BindingResult br, Model model) {
		Question qRes = questRep.save(question);
		model.addAttribute("question",qRes);
		return "questions/nice";
	}

}