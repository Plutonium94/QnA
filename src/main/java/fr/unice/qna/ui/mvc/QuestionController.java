package fr.unice.qna.ui.mvc;

import fr.unice.qna.persistence.*;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

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
		if(qRes != null) {
			// model.addAttribute("question",qRes);
			return "redirect:/questions/";
		} else {
			return "questions/new";
		}
	}

	@GetMapping("/")
	public String list(Model model) {
		List<Question> questions = questRep.findAllByOrderByTimestampDesc();
		model.addAttribute("questions", questions);
		return "questions/index";
	}

	@GetMapping("/{id}")
	public String view(@PathVariable("id") long id, Model model) {
		Optional<Question> questionOpt = questRep.findById(id);
		if(!questionOpt.isPresent()) {
			return "redirect:/questions/";
		} else {
			Question question = questionOpt.get();
			model.addAttribute("question",question);
			String title = question.getTitle();
			model.addAttribute("shortedTitle", (title.length() <= 8)?title: (title.substring(0,8) + "..."));
			return "questions/view";
		}
	}

	@PutMapping("/upvote/{id}")
	public @ResponseBody boolean upVote(@PathVariable("id") long id) {
		Optional<Question> questionOpt = questRep.findById(id);
		if(questionOpt.isPresent()) {
			questionOpt.get().upVote();
			return true;
		} else {
			return false;
		}
	}


}