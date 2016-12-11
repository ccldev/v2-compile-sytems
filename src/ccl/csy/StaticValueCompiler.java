package ccl.csy;

import ccl.csy.value.ValueCompiler;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class StaticValueCompiler {

	public static String compileValue(String val) throws DebugException, ImplementationException {
		ValueCompiler vc = new ValueCompiler(val);
		vc.act();
		return vc.get();
	}

}
