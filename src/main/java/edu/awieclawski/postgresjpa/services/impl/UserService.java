package edu.awieclawski.postgresjpa.services.impl;

import java.util.Arrays;
import java.util.HashSet;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.awieclawski.postgresjpa.config.AppRoles;
import edu.awieclawski.postgresjpa.entities.Role;
import edu.awieclawski.postgresjpa.entities.User;
import edu.awieclawski.postgresjpa.repositories.RoleRepository;
import edu.awieclawski.postgresjpa.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public User findUserByLogin(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
	}

	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		Role userRole = roleRepository.findByRole(AppRoles.USER.getRoleName());
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		return userRepository.save(user);
	}

}