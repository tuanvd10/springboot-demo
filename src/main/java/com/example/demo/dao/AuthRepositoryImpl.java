package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AccountSession;

import annotations.com.example.demo.entity.AccountSession_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

//Composing Repositories Using Multiple Fragments
//We just need to make sure that it has the postfix 'Impl'. 
//else, need define repositoryImplementationPostfix property of @EnableJpaRepositories annotation
//Furthermore, Spring Data JPA comes equipped with a query builder mechanism which provides the ability to generate queries on our behalf using method name conventions
//Example findAccountSesionByToken => JPA meaning find record by token field => cause error => attention when naming a function
@Repository
public class AuthRepositoryImpl implements AuthRepositoryCustom {
	@Autowired
	private EntityManager em;

	@Override
	public List<AccountSession> findAccountSesionByToken(String token) {
		// System.out.println("This is custom and can be use EM inhere");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AccountSession> cq = cb.createQuery(AccountSession.class);

		Root<AccountSession> accountSession = cq.from(AccountSession.class);
		Predicate tokenPredicate = cb.equal(accountSession.get(AccountSession_.ACCESS_TOKEN), token);
		cq.where(tokenPredicate);

		TypedQuery<AccountSession> query = em.createQuery(cq);
		return query.getResultList();
	}

}
