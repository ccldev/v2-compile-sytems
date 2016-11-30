package csy.value;

import java.util.ArrayList;
import java.util.regex.Pattern;

import csy.value.compile.RawValueCompiler;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class ValueType implements IValueTypes{
	
	static{
		list = new ArrayList<ValueType>();
		
		register(ARRAY);
		register(FUNC_LITERAL);
		register(NUMBER);
		register(REGEX);
		register(STRING);
		register(VARIABLE);
	}
	
	private static ArrayList<ValueType> list;
	public static ValueType[] values(){
		return list.toArray(new ValueType[0]);
	}
	public static void register(ValueType t){
		list.add(t);
	}
	
	char opener;
	char closer;
	private String charInfo;
	String valueInfo;
	public final RawValueCompiler compiler;

	public ValueType(char opener, char closer, RawValueCompiler compiler){
		this.opener = opener;
		this.closer = closer;
		this.compiler = compiler;
	}
	
	public ValueType(char openAndClose, RawValueCompiler compiler){
		this.opener = openAndClose;
		this.closer = openAndClose;
		this.compiler = compiler;
	}
	
	public ValueType(String charInfo, String valueInfo, RawValueCompiler compiler){
		this.charInfo = charInfo;
		this.valueInfo = valueInfo;
		this.compiler = compiler;
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
