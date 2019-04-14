package fr.unice.qna.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QnAUserRepository extends JpaRepository<QnAUser, String>, QnAUserRepositoryCustom {

}