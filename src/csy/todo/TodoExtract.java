package csy.todo;

import ccl.v2_1.err.ImplementationException;
import csy.todo.compile.TodoCustomCompiler;
import csy.todo.compile.TodoGetCompiler;
import csy.todo.compile.TodoInvokeCompiler;
import csy.value.compile.NativeValueCompiler;
import csy.value.compile.RawValueCompiler;

public class TodoExtract {
	
	private String val;
	private String todo;
	private TodoType type;
	
	public TodoExtract(String val, String todo, TodoType type){
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

	public TodoType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "TodoExtract [val=" + val + ", todo=" + todo + ", type=" + type
				+ "]";
	}

	public RawValueCompiler getCompiler() throws ImplementationException {
		switch(type){
		case GET: return new TodoGetCompiler();
		case INVOKE: return new TodoInvokeCompiler();
		case CUSTOM: return new TodoCustomCompiler();
		default: throw new ImplementationException("Unable to compute compiler for type " + type);
		}
	}
	
}
