package csy.todo.compile;

import csy.value.compile.RawValueCompiler;

public class TodoGetCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) {
		return "get " + val.substring(1).trim();
	}

}
