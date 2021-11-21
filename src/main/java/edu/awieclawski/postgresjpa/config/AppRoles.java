package edu.awieclawski.postgresjpa.config;

import lombok.Getter;

/**
 * User roles assign
 * 
 * @author AWieclawski
 * 
 *         http://tutorials.jenkov.com/java/enums.html
 */
@Getter
public enum AppRoles {

	ADMIN("ROLE_ADMIN"),

	USER("ROLE_USER");

	private final String roleName;

	AppRoles(String roleName) {
		this.roleName = roleName;
	}

}
