package psy;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import csy.StaticValueCompiler;
import csy.value.compile.RawValueCompiler;

public class LiteralCompiler implements RawValueCompiler {

	private String resultFormat;

	public LiteralCompiler(String resultFormat) {
		this.resultFormat = resultFormat;
	}

	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		val = val.substring(1,val.length() - 1);
		return StaticValueCompiler.compileValue(
				String.format(resultFormat, val)
				);
	}

}
