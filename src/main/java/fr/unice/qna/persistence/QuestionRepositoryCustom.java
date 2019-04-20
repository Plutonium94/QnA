package fr.unice.qna.persistence;

import javax.persistence.EntityManager;
import java.util.*;

public interface QuestionRepositoryCustom {

	Question create(String title, String content, String authorName);

	boolean upVote(long questionId);

	boolean downVote(long questionId);

	boolean addTag(long questionId, String tagName);

	boolean postNewAnswer(long questionId, String answerContent, String authorName);

	boolean postNewAnswer(Question question, Answer answer);

	boolean acceptAnswer(long questionId, long answerId);

	boolean rejectAcceptedAnswer(long questionId);

	SortedSet<Question> findRelatedQuestions(long questionId, int max);

	Set<Question> findRelatedQuestions(long questionId);


}