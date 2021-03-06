package ccl.csy.todo.compile;

import ccl.csy.StaticValueCompiler;
import ccl.csy.value.compile.RawValueCompiler;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class TodoInvokeCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) throws ImplementationException, DebugException {
		StringBuilder builder = new StringBuilder();
		
		val = val.trim();
		val = val.substring(1, val.length() - 1);
		ParameterList params = new ParameterList(val);
		for(int i = 0; i < params.count(); i++){
			builder.append(StaticValueCompiler.compileValue(params.get(i)));
			builder.append("\n");
		}
		switch(params.count()){
			case 0: builder.append("__invoke0"); break;
			case 1: builder.append("__invoke1"); break;
			case 2: builder.append("__invoke2"); break;
			default: builder.append("invoke " + params.count());
		}
		
		return builder.toString();
	}

}
