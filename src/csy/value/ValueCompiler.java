package csy.value;

import csy.StaticTodoCompiler;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class ValueCompiler {

	private String raw;
	private ValueExtract result;

	public ValueCompiler(String value) {
		this.raw = value.trim();
	}

	public void act() throws DebugException, ImplementationException {
		if(raw.length() == 0) throw new DebugException("A value may not be empty!");
		ValueType type = ValueType.compute(raw);
		ValueExtracter extracter = type.getExtracter();
		result = extracter.extract(raw);
	}

	public String get() throws ImplementationException, DebugException {
		String todo = StaticTodoCompiler.compileTodo(result.getTodo()).trim();
		return result.getCompiler().compileRawValue(result.getVal()) + (todo.isEmpty() ? "" : "\n") + 
				todo;
	}

}
