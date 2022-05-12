package gym.util;

import java.util.TreeMap;

import org.springframework.http.HttpStatus;

public class R extends TreeMap<String, Object>
{
	private static final long serialVersionUID = -2855780944246318500L;
	
	public static final String STATUS = "status";
	
	private R() { }
	
	public static R ok() { return ok("OK"); }
	
	public static R ok(String msg) { return raw(200, msg); }
	
	public static R error() { return error("An unexpected error has occurred :("); }
	
	public static R error(String msg) { return R.raw(500, msg); }
	
	public static R raw() { return new R(); }
	
	public static R raw(String msg) { return raw(); }
	
	public static R raw(HttpStatus status, String msg) { return raw(status.value(), msg); }
	
	public static R raw(int status, String msg) {
		return raw().add("message", msg).add("status", status);
	}
	
	public R add(String key, Object value)
	{
		this.put(key, value);
		return this;
	}
}
