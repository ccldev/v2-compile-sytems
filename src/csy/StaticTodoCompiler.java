package csy;

import csy.todo.TodoCompiler;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class StaticTodoCompiler {

	public static String compileTodo(String todo) throws DebugException, ImplementationException {
		TodoCompiler tc = new TodoCompiler(todo);
		tc.act();
		return tc.get();
	}

}
