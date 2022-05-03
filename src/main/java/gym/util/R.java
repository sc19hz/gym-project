package gym.util;

import java.util.TreeMap;

public class R extends TreeMap<String, Object>
{
	private static final long serialVersionUID = -2855780944246318500L;
	
	public static final String
		CODE = "code",
		MESSAGE = "msg";
	
	private R() { }
	
	public static R raw() { return new R(); }
	
	public static R ok() { return ok("OK"); }
	
	public static R ok(String msg) { return raw().add(CODE, 0).add(MESSAGE, msg); }
	
	public static R error() { return error("An unexpected error has occurred :("); }
	
	public static R error(String msg) { return error(500, msg); }
	
	public static R error(int code, String msg) { return raw().add(CODE, code).add(MESSAGE, msg); }
	
	public R add(String key, Object value)
	{
		this.put(key, value);
		return this;
	}
}
