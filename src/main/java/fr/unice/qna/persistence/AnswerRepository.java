package fr.unice.qna.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {
	
}
