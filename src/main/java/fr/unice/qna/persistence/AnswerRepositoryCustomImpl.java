package fr.unice.qna.persistence;

import javax.persistence.*;
import javax.transaction.Transactional;

public class AnswerRepositoryCustomImpl implements AnswerRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public boolean upVote(long answerId) {
		Answer answer = entityManager.find(Answer.class, answerId);
		if(answer == null) { return false; }
		int upVotes = answer.getUpVotes();
		answer.setUpVotes(upVotes + 1);
		return true;
	}

	@Transactional
	public boolean downVote(long answerId) {
		Answer answer = entityManager.find(Answer.class, answerId);
		if(answer == null) { return false; }
		int downVotes = answer.getDownVotes();
		answer.setDownVotes(downVotes + 1);
		return true;
	}
}