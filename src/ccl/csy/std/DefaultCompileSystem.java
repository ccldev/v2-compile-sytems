package ccl.csy.std;

import ccl.csy.value.ValueCompiler;
import ccl.v2.sys.CompileSystem;

public class DefaultCompileSystem implements CompileSystem{

	@Override
	public boolean accept(String snippet) {
		return true;
	}

	@Override
	public String compile(String snippet) {
		ValueCompiler c = new ValueCompiler(snippet.substring(0, snippet.length() - 1));
		c.act();
		return c.get() + "\nP";
	}

}
