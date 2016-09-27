package ccl.csy.ret;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.value.ValueCompiler;
import ccl.v2.sys.CompileSystem;

public class ReturnCompileSystem implements CompileSystem{

	private static final Pattern RETURN_PATTERN = Pattern.compile
			("\\s*return\\s+(.*)\\s*;\\s*");
	
	@Override
	public boolean accept(String snippet) {
		return RETURN_PATTERN.matcher(snippet).find();
	}

	@Override
	public String compile(String snippet) {
		Matcher m = RETURN_PATTERN.matcher(snippet);
		m.find();
		
		ValueCompiler vc = new ValueCompiler(m.group(1));
		vc.act();
		
		return vc.get() + "\nR:~";
	}

}
