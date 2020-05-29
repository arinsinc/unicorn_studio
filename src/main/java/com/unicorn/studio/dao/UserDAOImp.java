package com.unicorn.studio.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.unicorn.studio.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDAOImp implements UserDAO {
	// Create entity manager
	private EntityManager entityManager;

	@Autowired
	public UserDAOImp(EntityManager userEntity) {
		entityManager = userEntity;
	}

	@Override
	public List<User> getUsers() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<User> query = currentSession.createQuery("from user",User.class);
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	public void saveUser(User user) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(user);
	}

	@Override
	public User getUser(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		User user = currentSession.get(User.class, id);
		return user;
	}

	@Override
	public void deleteUser(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<?> query = currentSession.createQuery("delete from user where id=:userId");
		query.setParameter("userId", id);
		query.executeUpdate();
	}

}
