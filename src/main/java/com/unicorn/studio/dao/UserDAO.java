package com.unicorn.studio.dao;

import java.util.List;

import com.unicorn.studio.entity.User;

public interface UserDAO {

	public List<User> getUsers();

	public void saveUser(User user);

	public User getUser(int id);

	public void deleteUser(int id);

	
}
