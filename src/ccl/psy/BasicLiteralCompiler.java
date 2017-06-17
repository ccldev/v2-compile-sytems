package ccl.psy;

import ccl.csy.StaticValueCompiler;
import ccl.csy.value.compile.RawValueCompiler;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class BasicLiteralCompiler implements RawValueCompiler {

	private String resultFormat;

	public BasicLiteralCompiler(String resultFormat) {
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
