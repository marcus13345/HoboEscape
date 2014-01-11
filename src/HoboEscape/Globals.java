package HoboEscape;


public class Globals {
	public static boolean sound = Boolean.parseBoolean((new Variable("HoboExcape\\settings", "soundToggle", "true", false)).getValue());
	public static int soundInt = Integer.parseInt((new Variable("HoboExcape\\settings", "sound", "100", false)).getValue());
	
	
	
}