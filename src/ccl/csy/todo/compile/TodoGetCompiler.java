package ccl.csy.todo.compile;

import ccl.csy.value.compile.RawValueCompiler;

public class TodoGetCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) {
		return "get " + val.substring(1).trim();
	}

}
