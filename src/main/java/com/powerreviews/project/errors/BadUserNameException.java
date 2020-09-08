package com.powerreviews.project.errors;

public class BadUserNameException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1777090254075245052L;

	public BadUserNameException(String userName) {
        super("The use name given " + userName.toUpperCase() +  " is not exceptable and will be rejected");
    }

}
