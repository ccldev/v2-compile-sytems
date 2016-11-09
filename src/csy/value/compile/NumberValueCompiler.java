package csy.value.compile;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class NumberValueCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		return "put" + (val.contains(".") ? "F" : "I") + " " + val;
	}

}
