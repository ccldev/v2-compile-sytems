package ccl.csy.value.compile;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public interface RawValueCompiler {

	String compileRawValue(String val) throws ImplementationException, DebugException;

}
