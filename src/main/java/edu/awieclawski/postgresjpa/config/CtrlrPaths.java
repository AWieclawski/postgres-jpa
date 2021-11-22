package edu.awieclawski.postgresjpa.config;

/**
 * 
 * Paths used in controllers
 * 
 * @author AWieclawski
 *
 */
public class CtrlrPaths {
	/**
	 * Controller endpoint for public domain
	 */
	public final static String PUBLIC = "/public";

	/**
	 * Controller endpoint for secured domain
	 */
	public final static String SECURE = "/secured";

	/**
	 * Simple api rest endpoint for Customers
	 */
	public final static String API_CUSTOMERS = PUBLIC + "/restapi/customers";

	/**
	 * Simple api rest endpoint for changes registered by Javers
	 */
	public final static String API_CHANGES = PUBLIC + "/restapi/changes";

	/**
	 * Controller endpoint for login page
	 */
	public final static String LOGIN = "/login";

	/**
	 * Controller endpoint for logout page
	 */
	public final static String LOGOUT = "/logout";

	/**
	 * Controller endpoint for home page
	 */
	public final static String HOME = "/home";

	/**
	 * Controller endpoint for registration page
	 */
	public final static String REGSTR = "/registration";

}
