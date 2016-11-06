package csy.todo.compile;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import csy.StaticValueCompiler;
import csy.value.compile.RawValueCompiler;

public class TodoInvokeCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) throws ImplementationException, DebugException {
		StringBuilder builder = new StringBuilder();
		
		val = val.trim();
		val = val.substring(1, val.length() - 1);
		ParameterList params = new ParameterList(val);
		for(int i = 0; i < params.count(); i++){
			builder.append(StaticValueCompiler.compileValue(params.get(i)));
		}
		builder.append(";:invoke " + params.count() + " 1");
		
		return builder.toString();
	}

}
