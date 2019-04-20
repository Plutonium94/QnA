package fr.unice.qna.persistence;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.*;
import java.util.List;


public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	private Comparator<Question> questionComparator;

	public QuestionRepositoryCustomImpl() {
		this.questionComparator = new RecentQuestionComparator();
	}

	@Transactional
	public Question create(String title, String detail, String authorName) {
		QnAUser author = em.find(QnAUser.class, authorName);
		if(author == null) { System.out.printf("x%sx author not found", authorName); return null; }
		Question res = new Question(title, detail);
		res.setAuthor(author);
		em.persist(res);
		return res;
	}

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
		SortedSet<Tag> tags = question.getTags();

		Tag tagToBeAdded = em.find(Tag.class, tagName);
		if(tagToBeAdded == null) {
			tagToBeAdded = new Tag(tagName);
		}
		tagToBeAdded.getAssociatedQuestions().add(question);

		tags.add(tagToBeAdded);
		em.persist(question);
		return true;
	}

	@Transactional
	public boolean postNewAnswer(long questionId, String answerContent, String authorName) {
		QnAUser author = em.find(QnAUser.class, authorName);
		if(author == null) { return false; }
		Question question = em.find(Question.class, questionId);
		if(question == null) { return false; }
		Answer answer = new Answer(answerContent);
		answer.setAuthor(author);
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

	@Transactional
	public boolean rejectAcceptedAnswer(long questionId) {
		Question question = em.find(Question.class, questionId);
		if(question == null) { return false; }
		question.setAcceptedAnswer(null);
		return true;
	}

	@Transactional
	public SortedSet<Question> findRelatedQuestions(long questionId, int max) {
		Question question = em.find(Question.class, questionId);
		SortedSet<Tag> tags = question.getTags();

		int resLength = 0;
		SortedSet<Question> res = new TreeSet<Question>(questionComparator); 

		for(Tag t : tags) {
			Set<Question> associatedQuestions = t.getAssociatedQuestions();
			System.out.println(t.getName() + ": associated questions : " +associatedQuestions);
			for(Question q : associatedQuestions) {
				if(q.getId() != question.getId()) {
					res.add(q);
					if(++resLength >= max) {
						return res;
					}
				}
			}
		}
		System.out.println("findRelatedQuestions");
		System.out.println(res);
		return res;
	}

	@Transactional
	public Set<Question> findRelatedQuestions(long questionId) {
		Set<Tag> res = new TreeSet<Tag>();
		/*Question question = em.find(Question.class, questionId);
		Set<Tag> tags = question.getTags();
		Set<String> tagNames = new TreeSet<String>();
		for(Tag t : tags) {
			tagNames.add(t.getName());
		}*/
		List<Question> resList = em.createQuery("Select q from Question q join q.tags t where t.name in (select  t2.name from Question q2 join q2.tags t2 where q2.id = :qid ) and q.id != :qid", Question.class)
			// .setParameter("names",tagNames)
			.setParameter("qid",questionId)
			.getResultList();
		return new HashSet<Question>(resList);
	}

	private class RecentQuestionComparator implements Comparator<Question> {

		public int compare(Question q1, Question q2) {
			return (int)(q1.getTimestamp() - q2.getTimestamp());
		}
	}



}