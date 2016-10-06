package csy.value;

import java.util.regex.Pattern;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public enum ValueType {
	
	NATIVE('(', ')'),
	ARRAY('[', ']'),
	NUMBER("\\d", "\\d+"),
	STRING('"'),
	VARIABLE("[a-zA-Z0-9_]", "[a-zA-Z0-9_]+");
	
	char opener;
	char closer;
	private String charInfo;
	String valueInfo;

	private ValueType(char opener, char closer){
		this.opener = opener;
		this.closer = closer;
	}
	
	private ValueType(char openAndClose){
		this.opener = openAndClose;
		this.closer = openAndClose;
	}
	
	private ValueType(String charInfo, String valueInfo){
		this.charInfo = charInfo;
		this.valueInfo = valueInfo;
	}
	
	public boolean matches(char c){
		if(usesOpenAndClose()){
			return opener == c;
		}
		return Pattern.matches(charInfo, String.valueOf(c));
	}

	public static ValueType compute(String raw) throws DebugException {
		char start = raw.charAt(0);
		for(int i = 0; i < values().length; i++){
			ValueType t = values()[i];
			if(t.matches(start)) return t;
		}
		throw new DebugException("No matching value type found! Input:\n" + raw);
	}
	
	boolean usesOpenAndClose(){
		return charInfo == null;
	}

	public ValueExtracter getExtracter() throws ImplementationException {
		return new ValueExtracter(this);
	}
	
}
