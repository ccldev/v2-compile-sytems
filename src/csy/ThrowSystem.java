package csy;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v2_1.cat.CclCodeSnippet;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.sys.CompileSystem;

public class ThrowSystem implements CompileSystem<CclCodeSnippet, File>{

	private static final Pattern THRW_PATTERN = Pattern.compile
			("\\s*throw\\s+(.+)\\s*;\\s*", Pattern.DOTALL);
	
	@Override
	public boolean accept(CclCodeSnippet infos) {
		return THRW_PATTERN.matcher(infos.getRaw()).matches();
	}

	@Override
	public String compileComplete(CclCodeSnippet infos)
			throws ImplementationException, DebugException, IOException {
		Matcher m = THRW_PATTERN.matcher(infos.getRaw());
		m.matches();
		
		StringBuilder builder = new StringBuilder();
		builder.append(StaticValueCompiler.compileValue(m.group(1)));
		builder.append("\nload error\ninvoke 1\nret");
		
		return builder.toString();
	}

	@Override
	public File getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String include() {
		// TODO Auto-generated method stub
		return null;
	}

}
