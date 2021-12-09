package edu.awieclawski.postgresjpa.credentials.services.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.awieclawski.postgresjpa.credentials.entities.User;
import edu.awieclawski.postgresjpa.credentials.entities.UserRegistration;
import edu.awieclawski.postgresjpa.credentials.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException(" UserDetailsService - User not found " + username));

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (UserRegistration userReg : user.getRegistrations()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(userReg.getRole().getName()));
		}

		log.warn(" -- LUserDetailsServiceImpl - user=" + user.toString() + " \n passCrypt=" + user.getPassCrypt()
				+ "\n password=" + user.getPassword());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassCrypt(),
				grantedAuthorities);
	}
}
