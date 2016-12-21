package ccl.csy.todo;

import java.util.regex.Pattern;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public enum TodoType {
	
	GET("\\.", "\\.\\s*([a-zA-Z0-9_]+)"),
	INVOKE('(', ')'),
	GET_DYNAMIC('[',']'),
	EMPTY(null, null),
	COMMENT("/", "//[^\n]*"),
	CUSTOM;
	
	char opener;
	char closer;
	private String charInfo;
	String valueInfo;

	private TodoType(){};
	
	private TodoType(char opener, char closer){
		this.opener = opener;
		this.closer = closer;
	}
	
	private TodoType(char openAndClose){
		this.opener = openAndClose;
		this.closer = openAndClose;
	}
	
	private TodoType(String charInfo, String valueInfo){
		this.charInfo = charInfo;
		this.valueInfo = valueInfo;
	}
	
	public boolean matches(char c){
		if(usesOpenAndClose()){
			return opener == c;
		}
		return Pattern.matches(charInfo, String.valueOf(c));
	}

	public static TodoType compute(String raw) throws DebugException {
		if(raw.trim().length() == 0) return EMPTY;
		char start = raw.charAt(0);
		for(int i = 0; i < values().length; i++){
			TodoType t = values()[i];
			if(t == CUSTOM) continue;
			if(t.matches(start)) return t;
		}
		return CUSTOM;
	}
	
	boolean usesOpenAndClose(){
		return charInfo == null;
	}
	
	public TodoExtracter getExtracter() throws ImplementationException {
		return new TodoExtracter(this);
	}
	
}
