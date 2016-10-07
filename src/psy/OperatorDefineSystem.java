package psy;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import csy.Operators;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.sys.CompileSystem;

public class OperatorDefineSystem implements CompileSystem<String, Void>{

	private static final Pattern OPDEF_PATTERN = Pattern.compile
			("\\s*#\\s*defop\\s+([^\\s]+)\\s+([^\\s]+)\\s*");
	
	@Override
	public boolean accept(String infos) {
		return OPDEF_PATTERN.matcher(infos).matches();
	}

	@Override
	public String compileComplete(String infos) throws ImplementationException,
			DebugException, IOException {
		Matcher m = OPDEF_PATTERN.matcher(infos);
		m.matches();
		Operators.add(m.group(1), m.group(2));
		return "";
	}

	@Override
	public Void getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String include() {
		// TODO Auto-generated method stub
		return null;
	}

}
