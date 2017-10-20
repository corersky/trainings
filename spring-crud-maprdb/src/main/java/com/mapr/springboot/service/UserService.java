package com.mapr.springboot.service;


import com.mapr.springboot.model.User;

import java.util.List;

public interface UserService {
	
	User findById(String id);

	User findByName(String name);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserById(String id);

	void deleteAllUsers();

	List<User> findAllUsers();

	boolean isUserExist(User user);
}