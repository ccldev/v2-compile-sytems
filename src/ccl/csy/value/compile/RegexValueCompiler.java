package ccl.csy.value.compile;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class RegexValueCompiler implements RawValueCompiler {

	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		String regex = val.substring(1, val.length() - 1);
		
		StringBuilder b = new StringBuilder();
		b.append("load regex\nputS ");
		b.append(regex);
		b.append("\ninvoke 1");
		
		return b.toString();
	}

}
