package edu.awieclawski.postgresjpa.credentials.services;

import edu.awieclawski.postgresjpa.credentials.entities.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);

}