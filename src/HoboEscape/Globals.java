package HoboEscape;

/**
 * simple clas s for in game settings that load in from variables. not actually associated with anything yet because
 * sound itself hasnt been implemented.
 * @author mgosselin
 *
 */
public class Globals {
	public static boolean sound = Boolean.parseBoolean((new Variable("HoboExcape\\settings", "soundToggle", "true", false)).getValue());
	public static int soundInt = Integer.parseInt((new Variable("HoboExcape\\settings", "sound", "100", false)).getValue());
	
	
	
}