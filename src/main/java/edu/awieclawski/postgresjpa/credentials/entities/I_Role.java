package edu.awieclawski.postgresjpa.credentials.entities;

import javax.persistence.Transient;

import edu.awieclawski.postgresjpa.config.AppRoles;
//import lombok.Builder;

public interface I_Role {
	
	@Transient
//	@Builder.Default
	public static String DEFAULT_ROLENAME = AppRoles.USER.getRoleName();


}
