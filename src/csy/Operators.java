package csy;

import java.util.HashMap;

import ccl.v2_1.err.DebugException;

public class Operators {

	private static HashMap<String, String> map = new HashMap<>();
	
	public static String lookup(String op) throws DebugException {
		String res = map.get(op);
		if(res == null){
			throw new DebugException("No such operator found: " + op);
		}
		return res;
	}
	
	public static void add(String op, String func){
		map.put(op, func);
	}
	
}
