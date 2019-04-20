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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Optional;

@RequestMapping("/questions")
@Controller
public class QuestionController {

	@Autowired
	private QuestionRepository questRep;

	@Autowired
	private TagRepository tagRep;

	@Autowired
	private QnAUserRepository userRep;

	@RequestMapping(value = "/new", method={RequestMethod.GET})
	public String showNewForm(Model model) {
		model.addAttribute("question", new Question());
		return "questions/new";
	}	

	@PostMapping("/new")
	public String newFormSubmit(@ModelAttribute Question question, BindingResult br, Model model, Principal p) {
		QnAUser author = userRep.getOne(p.getName());
		question.setAuthor(author);
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
	public String view(@PathVariable("id") long id, Model model, Principal p) {
		System.out.println(p);
		Optional<Question> questionOpt = questRep.findById(id);
		if(!questionOpt.isPresent()) {
			return "redirect:/questions/";
		} else {
			Question question = questionOpt.get();

			QnAUser author = question.getAuthor();
			boolean viewerIsAuthor = p != null && author != null  && p.getName().equals(author.getUsername());
    
			model.addAttribute("question",question);
			model.addAttribute("viewerIsAuthor", viewerIsAuthor);
			model.addAttribute("shortedTitle", shorten(question.getTitle(), 8));
			model.addAttribute("allTags",tagRep.findAll());
			model.addAttribute("newAnswer", new Answer());
			model.addAttribute("relatedQuestions", questRep.findRelatedQuestions(question.getId()));
			return "questions/view";
		}
	}

	@PutMapping("/upvote/{id}")
	public @ResponseBody boolean upVote(@PathVariable("id") long id) {
		return questRep.upVote(id);
	}

	@PutMapping("/downvote/{id}")
	public @ResponseBody boolean downVote(@PathVariable("id") long id) {
		return questRep.downVote(id);
	}

	@PutMapping("/{id}/addTag")
	public @ResponseBody boolean addTag(@PathVariable("id") long id, @RequestBody String tagName) {
		return questRep.addTag(id, tagName);
	}

	@PostMapping("/{id}/new-answer")
	public String postNewAnswer(@PathVariable("id") long id,  @ModelAttribute("newAnswer") Answer newAnswer, @ModelAttribute("question") Question question, Model model, BindingResult br, Principal p) {
		System.out.println(newAnswer);
		// System.out.println(model);
		// questRep.postNewAnswer(question, newAnswer);
		questRep.postNewAnswer(id, newAnswer.getContent(), p.getName());
		return "redirect:/questions/{id}";
	}

	@PostMapping("/{qid}/accept/{aid}")
	public @ResponseBody boolean acceptAnswer(@PathVariable("qid") long qid, @PathVariable("aid") long aid) {
		return questRep.acceptAnswer(qid, aid);
	}

	@PostMapping("/{qid}/reject-accepted-answer")
	public @ResponseBody boolean rejectAcceptedAnswer(@PathVariable("qid") long qid) {
		return questRep.rejectAcceptedAnswer(qid);
	}

	/*@PostMapping("/{id}/new-answer")
	public @ResponseBody boolean postNewAnswer(@PathVariable("id") long id, String answerContent) {
		return questRep.postNewAnswer(id,answerContent);
	}*/

	private static String shorten(String s, int shortenedLength) {
		return (s.length() <= shortenedLength)?s: (s.substring(0,shortenedLength) + "...");
	}

}