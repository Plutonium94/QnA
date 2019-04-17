package fr.unice.qna.persistence;

import javax.persistence.EntityManager;


public interface QuestionRepositoryCustom {

	boolean upVote(long questionId);

	boolean downVote(long questionId);

	boolean addTag(long questionId, String tagName);

	boolean postNewAnswer(long questionId, String answerContent, String authorName);

	boolean postNewAnswer(Question question, Answer answer);

	boolean acceptAnswer(long questionId, long answerId);

	boolean rejectAcceptedAnswer(long questionId);


}