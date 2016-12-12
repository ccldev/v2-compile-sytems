package ccl.csy.value.compile;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class StringValueCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		val = val.trim();
		val = val.substring(1, val.length() - 1);
		if(val.endsWith(" ")){
			val = val + "\\";
		}
		return "putS " + val;
	}
	
}
