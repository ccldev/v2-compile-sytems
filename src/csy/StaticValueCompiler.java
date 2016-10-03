package csy;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.err.NI;
import csy.value.ValueCompiler;

public class StaticValueCompiler {

	public static String compileValue(String val) throws DebugException, ImplementationException {
		ValueCompiler vc = new ValueCompiler(val);
		vc.act();
		return vc.get();
	}

}
