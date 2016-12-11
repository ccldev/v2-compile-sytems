package ccl.csy.value;

import ccl.csy.value.compile.RawValueCompiler;
import ccl.v2_1.err.ImplementationException;

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
		return type.compiler;
	}
	
}
