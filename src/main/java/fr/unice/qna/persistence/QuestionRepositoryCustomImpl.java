package fr.unice.qna.persistence;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.*;
import java.util.List;


public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public boolean upVote(long questionId) {
		Question question = em.find(Question.class, questionId);
		if(question == null) { return false; }
		em.createQuery("Update Question q set q.upVotes = q.upVotes + 1 where q.id = :id")
			.setParameter("id",questionId)
			.executeUpdate();
		return true;
	}

	@Transactional
	public boolean downVote(long questionId) {
 		Question question = em.find(Question.class, questionId);
 		if(question == null) { return false; }
 		em.createQuery("Update Question q Set q.downVotes = q.downVotes + 1 where q.id = :id")
 			.setParameter("id", questionId)
 			.executeUpdate();
 		return true;
	}

	@Transactional
	public boolean addTag(long questionId, String tagName) {
		Question question = em.find(Question.class, questionId);
		if(question == null) { return false; }
		Set<Tag> tags = question.getTags();

		Tag tagToBeAdded = em.find(Tag.class, tagName);
		if(tagToBeAdded == null) {
			tagToBeAdded = new Tag(tagName);
		}

		tags.add(tagToBeAdded);
		em.persist(question);
		return true;
	}

	@Transactional
	public boolean postNewAnswer(long questionId, String answerContent) {
		Question question = em.find(Question.class, questionId);
		if(question == null) { return false; }
		Answer answer = new Answer(answerContent);
		List<Answer> existingAnswers = question.getAnswers();
		existingAnswers.add(answer);
		return true;
	}

	@Deprecated
	@Transactional
	public boolean postNewAnswer(Question question, Answer answer) {
		em.merge(answer);
		// System.out.println(answer);
		// em.refresh(question);
		List<Answer> existingAnswers = question.getAnswers();
		existingAnswers.add(answer);
		question.setAnswers(existingAnswers);	
		em.merge(question);
		return true;
	}

	@Transactional
	public boolean acceptAnswer(long questionId, long answerId) {
		Question question = em.find(Question.class, questionId);
		if(question == null) { return false; }
		Answer acceptedAnswer = em.find(Answer.class, answerId);
		if(acceptedAnswer == null) { return false; }
		question.setAcceptedAnswer(acceptedAnswer);
		return true;
	}

}