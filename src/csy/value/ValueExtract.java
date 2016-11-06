package csy.value;

import ccl.v2_1.err.ImplementationException;
import csy.value.compile.ArrayValueCompiler;
import csy.value.compile.NumberValueCompiler;
import csy.value.compile.RawValueCompiler;
import csy.value.compile.StringValueCompiler;
import csy.value.compile.VariableValueCompiler;

public class ValueExtract {
	
	private String val;
	private String todo;
	private ValueType type;
	
	public ValueExtract(String val, String todo, ValueType type){
		this.val = val;
		this.todo = todo;
		this.type = type;
	}

	public String getVal() {
		return val;
	}

	public String getTodo() {
		return todo;
	}

	public ValueType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "ValueExtract [val=" + val + ", todo=" + todo + ", type=" + type
				+ "]";
	}

	public RawValueCompiler getCompiler() throws ImplementationException {
		switch(type){
		case STRING: return new StringValueCompiler();
		case VARIABLE: return new VariableValueCompiler();
		case ARRAY: return new ArrayValueCompiler();
		case NUMBER: return new NumberValueCompiler();
		default: throw new ImplementationException("Unable to compute compiler for type " + type);
		}
	}
	
}
