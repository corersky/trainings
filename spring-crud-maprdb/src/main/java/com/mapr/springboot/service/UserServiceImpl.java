package com.mapr.springboot.service;

import java.util.List;

import com.mapr.springboot.model.User;
import com.mapr.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	public User findById(String id) {
		return userRepository.findOne(id);
	}

	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public void updateUser(User user){
		saveUser(user);
	}

	public void deleteUserById(String id){
		userRepository.delete(id);
	}

	public void deleteAllUsers(){
		userRepository.deleteAll();
	}

	public List<User> findAllUsers(){
		return userRepository.findAll();
	}

	public boolean isUserExist(User user) {
		return findByName(user.getName()) != null;
	}

}
