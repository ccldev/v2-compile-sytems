package ccl.csy.value.compile;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class VariableValueCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		if(val.equals("true")){
			return "putI 1";
		}else if(val.equals("false")){
			return "putI 0";
		}
		return "load " + val;
	}

}
