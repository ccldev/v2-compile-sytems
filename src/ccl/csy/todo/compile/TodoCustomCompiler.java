package ccl.csy.todo.compile;

import ccl.csy.context.Alias;
import ccl.csy.Constants;
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

		boolean abcOp = false;
		loop:
		for(int i = 0; i < val.length(); i++){
			char c = val.charAt(i);
			if(Constants.OPERATOR_CHARS.contains(c + "")){
				op += c;
			}else if(op.isEmpty() || abcOp){
				if((c + "").trim().isEmpty()){
					break loop;
				}else{
					op += c;
					abcOp = true;
				}
			}else{
				break;
			}
		}
		String value = val.substring(op.length()).trim();
		return buildResult(op, value.trim());
	}

	private String buildResult(String opMethod, String value) throws DebugException, ImplementationException {
		StringBuilder builder = new StringBuilder();

		String alias = Alias.lookup(opMethod);
		if(alias != null){
			builder.append(StaticValueCompiler.compileValue(alias) + "\n");
		}else{
			builder.append("load ");
			builder.append(opMethod);
			builder.append("\n");
		}

		if(value.trim().isEmpty()){
			builder.append("invoke1 1");
		}else{
			builder.append("get bind\n");
			//operator.bind
			builder.append("invoke1 1\n");
			//operator.bind(a)
			builder.append(StaticValueCompiler.compileValue(value));
			builder.append("\n__invoke1");
			//operator.bind(a)(b)
		}

		return builder.toString();
	}

}
