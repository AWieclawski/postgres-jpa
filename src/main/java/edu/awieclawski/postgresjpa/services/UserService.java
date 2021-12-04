package edu.awieclawski.postgresjpa.services;

import edu.awieclawski.postgresjpa.entities.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);

}