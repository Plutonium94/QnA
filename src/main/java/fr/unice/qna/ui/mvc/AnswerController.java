package fr.unice.qna.ui.mvc;

import fr.unice.qna.persistence.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AnswerController {

	@Autowired
	private AnswerRepository answerRepository;

	@PutMapping("/answer/{id}/upVote")
	public @ResponseBody boolean upVote(@PathVariable("id") long answerId) {
		return answerRepository.upVote(answerId);
	}

	@PutMapping("/answer/{id}/downVote")
	public @ResponseBody boolean downVote(@PathVariable("id") long answerId) {
		return answerRepository.downVote(answerId);
	}


}