package HoboEscape;

/**
 * gets thrown when you say MAKE A VARIABLE, but instead of existing... it doesn't.
 * also totally shouldn't happen anymore because now, it will create the variable for you.
 * 
 * @author mgosselin
 *
 */
public class VariableNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Variable not found";
	}
}
