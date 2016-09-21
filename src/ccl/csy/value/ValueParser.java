package ccl.csy.value;

import java.util.HashMap;

public abstract class ValueParser {

	private static HashMap<ValueType, ValueParser> map = new HashMap<>();
	
	public static ValueParser get(ValueType type) {
		return map.get(type);
	}
	
	public static void add(ValueType t, ValueParser p){
		map.put(t, p);
	}

	public abstract void feed(String val);
	public abstract String getBase();
	public abstract String getTodo();

}
