package ccl.csy.value.compile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class UnboundValueCompiler implements RawValueCompiler {

	private static final Pattern NAME_PATTERN = Pattern.compile("\\.\\s*(.*)\\s*");
	
	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		Matcher m = NAME_PATTERN.matcher(val);
		m.matches();
		
		return 	"load unbound" + 
				"\nputS " + m.group(1) + 
				"\ninvoke 1";
	}

}
