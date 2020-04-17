package com.unicorn.studio.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.unicorn.studio.entity.User;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public class UserDAOImp implements UserDAO {
	// Inject session factory
    
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<User> getUsers() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<User> query = currentSession.createQuery("from user",User.class);
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(user);
	}

	@Override
	@Transactional
	public User getUser(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		User user = currentSession.get(User.class, id);
		return user;
	}

	@Override
	@Transactional
	public void deleteUser(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<?> query = currentSession.createQuery("delete from user where id=:id");
		query.setParameter("userId", id);
		query.executeUpdate();
	}

}
