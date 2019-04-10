package fr.unice.qna.persistence;

public interface AnswerRepositoryCustom {

	public boolean upVote(long answerId);

	public boolean downVote(long answerId);
}