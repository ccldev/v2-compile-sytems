package ccl.csy.variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.value.ValueCompiler;
import ccl.v2.sys.CompileSystem;

public class VariableCompileSystem implements CompileSystem{

	public static final Pattern VAR_PATTERN = Pattern.compile
			("\\s*var\\s+([a-zA-Z][a-zA-Z0-9_]*)\\s*=\\s*(.*)\\s*;", Pattern.DOTALL);
	
	public boolean accept(String snippet) {
		Matcher m = VAR_PATTERN.matcher(snippet);
		return m.find();
	}

	public String compile(String snippet) {
		VariableInfo info = new VariableInfo(VAR_PATTERN.matcher(snippet));
		ValueCompiler c = new ValueCompiler(info.getValue());
		c.act();
		return c.get() + "\nV1::" + info.getName() + ":#";
	}

}
