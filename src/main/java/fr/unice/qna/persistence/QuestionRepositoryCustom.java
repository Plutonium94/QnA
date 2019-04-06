package fr.unice.qna.persistence;

import javax.persistence.EntityManager;


public interface QuestionRepositoryCustom {

	boolean upVote(long questionId);

	boolean downVote(long questionId);

}