package edu.awieclawski.postgresjpa.credentials.services;

import java.util.Collection;

import edu.awieclawski.postgresjpa.credentials.entities.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);

	User ifUserExists(User user);

	Collection<User> findAll();

}