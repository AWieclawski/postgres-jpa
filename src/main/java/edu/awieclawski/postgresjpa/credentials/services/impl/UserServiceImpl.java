package edu.awieclawski.postgresjpa.credentials.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.awieclawski.postgresjpa.credentials.entities.Role;
import edu.awieclawski.postgresjpa.credentials.entities.User;
import edu.awieclawski.postgresjpa.credentials.entities.UserRegistration;
import edu.awieclawski.postgresjpa.credentials.repositories.RoleRepository;
import edu.awieclawski.postgresjpa.credentials.repositories.UserRegistrationRepository;
import edu.awieclawski.postgresjpa.credentials.repositories.UserRepository;
import edu.awieclawski.postgresjpa.credentials.services.UserService;

/**
 * Used by registration form
 * 
 * @author AWieclawski
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		user.setPassCrypt(bCryptPasswordEncoder.encode(user.getPassword()));

		if (user.getRegistrations() == null || user.getRegistrations().size() < 1)
			user.addUserRegistration(UserRegistration.builder().role(getDefaulRole()).build());

		userRepository.save(user);
		saveUserRegistrationRepositories(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException(" - User not found =" + username));
	}

	@Override
	public User ifUserExists(User user) {
		boolean result = true;
		try {
			findByUsername(user.getUsername());
		} catch (Exception e) {
			log.error(e.getMessage());
			result = false;
		}
		user.setUserExists(result);
		return user;
	}

	private Role getDefaulRole() {
		return roleRepository.findByName(Role.DEFAULT_ROLENAME)
				.orElseThrow(() -> new EntityNotFoundException(" - Role not found =" + Role.DEFAULT_ROLENAME));
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	private void saveUserRegistrationRepositories(User user) {
		user.getRegistrations().stream().forEach(ureg -> {
			userRegistrationRepository.save(ureg);
		});
	}
}
