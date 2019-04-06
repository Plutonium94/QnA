package fr.unice.qna.persistence;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
}