package csy.todo.compile;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import csy.StaticValueCompiler;
import csy.value.compile.RawValueCompiler;

public class TodoDynamicGetCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		String raw = val.substring(1, val.length() - 1).trim();
		
		StringBuilder b = new StringBuilder();
		b.append("get property\n");
		b.append(StaticValueCompiler.compileValue(raw));
		b.append("\ninvoke 1");
		
		return b.toString();		
	}

}
