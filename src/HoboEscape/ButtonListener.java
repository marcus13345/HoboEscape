package HoboEscape;

/**
 * the interface that says hey buttons, you can send your clicks to this guy!
 * @author mgosselin
 *
 */
public interface ButtonListener {
	public abstract void click(String name, int id);
}
