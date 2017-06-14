package ccl.csy.value.compile;

import ccl.csy.context.Alias;
import ccl.csy.StaticValueCompiler;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class VariableValueCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		String alias = Alias.lookup(val);

		if(alias != null){
			return StaticValueCompiler.compileValue(alias);
		}

		return "load " + val;
	}

}
