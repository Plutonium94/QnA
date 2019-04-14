package fr.unice.qna.persistence;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

public class QnAUserRepositoryCustomImpl implements QnAUserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public UserDetails loadUserByUsername(String username) {
		return entityManager.find(QnAUser.class, username);
	}
}