package utils;

import entities.User;

public class EmptyUser extends User {
	
	public static Integer emptyId = -1;
	public static String emptyFirstName = "no-firstname";
	public static String emptyEmail = "no-email";
	public static String emptyLastName = "no-lastname";
	public static String emptyUserName = "no-username";
	public static String emptyPassword = "no-password";
	public static boolean emptyIsActive = false;
	
	public EmptyUser() {
		this.setId( emptyId );
		this.setFirstName( emptyFirstName );
		this.setEmail( emptyEmail );
		this.setLastName( emptyLastName );
		this.setPassword( emptyUserName );
		this.setUserName( emptyPassword );
		this.setIsActive( emptyIsActive );
	}
}
