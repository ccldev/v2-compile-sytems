package ccl.csy;

import java.util.HashMap;

import ccl.v2_1.err.DebugException;

public class Alias {

	private static HashMap<String, String> map = new HashMap<>();

	public static String lookup(String alias) throws DebugException {
		return map.get(alias.trim());
	}

	public static void add(String alias, String orig) throws DebugException{
		String s = map.get(alias.trim());
		if(s != null){
			throw new DebugException("Alias duplication: " + alias);
		}
		map.put(alias, orig);
	}

}
