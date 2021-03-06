package fr.unice.qna.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {

	List<Question> findByTitle(String title);

	List<Question> findAllByOrderByTimestampDesc();



}