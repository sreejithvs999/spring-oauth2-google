package com.svs.learn.guser.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.svs.learn.guser.dao.entity.GUser;
import com.svs.learn.guser.dao.entity.UserRole;

@Repository
public class GUserDao {

	@PersistenceContext
	EntityManager entityManager;

	public GUser saveL7User(GUser user) {
		entityManager.persist(user);
		entityManager.flush();
		return user;
	}

	public GUser getUserByEmail(String emailId) {
		List<GUser> users = entityManager.createQuery("from GUser gu where gu.emailId =:email", GUser.class)
				.setParameter("email", emailId).getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

	public GUser getUserForEdit(Integer userId) {
		List<GUser> users = entityManager
				.createQuery("select l7u from GUser gu left join fetch gu.roles where gu.userId=:userId",
						GUser.class)
				.setParameter("userId", userId).getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

	public List<UserRole> getUserRoles(String emailId) {
		return entityManager
				.createQuery("select distinct r from GUser u join u.roles r where u.emailId=:emailId", UserRole.class)
				.setParameter("emailId", emailId).getResultList();
	}
}
