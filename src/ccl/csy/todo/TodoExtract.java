package ccl.csy.todo;

import ccl.csy.todo.compile.TodoCustomCompiler;
import ccl.csy.todo.compile.TodoDynamicGetCompiler;
import ccl.csy.todo.compile.TodoGetCompiler;
import ccl.csy.todo.compile.TodoInvokeCompiler;
import ccl.csy.value.compile.RawValueCompiler;
import ccl.v2_1.err.ImplementationException;

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
		case GET_DYNAMIC: return new TodoDynamicGetCompiler();
		case INVOKE: return new TodoInvokeCompiler();
		case CUSTOM: return new TodoCustomCompiler();
		default: throw new ImplementationException("Unable to compute compiler for type " + type);
		}
	}
	
}
