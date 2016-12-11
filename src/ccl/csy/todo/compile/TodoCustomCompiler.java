package ccl.csy.todo.compile;

import ccl.csy.Constants;
import ccl.csy.Operators;
import ccl.csy.StaticValueCompiler;
import ccl.csy.value.compile.RawValueCompiler;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class TodoCustomCompiler implements RawValueCompiler {
	
	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		if(val.trim().isEmpty()) return "";
		String op = "";
		for(int i = 0; i < val.length(); i++){
			char c = val.charAt(i);
			if(Constants.OPERATOR_CHARS.contains(c + "")){
				op += c;
			}else{
				break;
			}
		}
		String value = val.substring(op.length()).trim();
		return buildResult(Operators.lookup(op), value.trim());
	}

	private String buildResult(String opMethod, String value) throws DebugException, ImplementationException {
		StringBuilder builder = new StringBuilder();
		builder.append("get ");
		builder.append(opMethod);
		builder.append("\n");
		if(!value.isEmpty()){
			builder.append(StaticValueCompiler.compileValue(value));
			builder.append("\ninvoke 1");
		}else{
			builder.append("invoke 0");
		}
		return builder.toString();
	}

}
