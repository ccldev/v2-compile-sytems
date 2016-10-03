package csy.todo;

import csy.StaticTodoCompiler;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.err.NI;

public class TodoCompiler {

	private String todo;
	private TodoExtract result;

	public TodoCompiler(String todo) {
		this.todo = todo.trim();
	}

	public void act() throws DebugException, ImplementationException {
		TodoType type = TodoType.compute(todo);
		result = type.getExtracter().extract(todo);
	}

	public String get() throws ImplementationException, DebugException {
		if(result.getType() == TodoType.EMPTY) return "";
		return result.getCompiler().compileRawValue(result.getVal()) + "\n" + StaticTodoCompiler.compileTodo(result.getTodo());
	}

}
