package ccl.csy.variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.value.ValueCompiler;
import ccl.v2.sys.CompileSystem;

public class VarSetCompileSystem implements CompileSystem{

	private static final Pattern VARSET_PATTERN = Pattern.compile
			("\\s*([a-zA-Z][a-zA-Z0-9_]*)\\s*=\\s*(.*)\\s*;", Pattern.DOTALL);
	
	@Override
	public boolean accept(String snippet) {
		Matcher m = VARSET_PATTERN.matcher(snippet);
		return m.find();
	}

	@Override
	public String compile(String snippet) {
		Matcher m = VARSET_PATTERN.matcher(snippet);
		m.find();
		ValueCompiler vc = new ValueCompiler(m.group(2));
		vc.act();
		String ret = vc.get() + "\nV0::" + m.group(1) + ":#";
		return ret;
	}

}
