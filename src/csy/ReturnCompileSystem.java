package csy;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v2_1.cat.CclCodeSnippet;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.sys.CompileSystem;

public class ReturnCompileSystem implements CompileSystem<CclCodeSnippet, File>{

	private static final Pattern RET_PATTERN = Pattern.compile
			("\\s*return\\s+(.+)\\s*;\\s*", Pattern.DOTALL);
	
	@Override
	public boolean accept(CclCodeSnippet infos) {
		return RET_PATTERN.matcher(infos.getRaw()).matches();
	}

	@Override
	public String compileComplete(CclCodeSnippet infos)
			throws ImplementationException, DebugException, IOException {
		Matcher m = RET_PATTERN.matcher(infos.getRaw());
		m.matches();
		return StaticValueCompiler.compileValue(m.group(1)) + "\nR:~";
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
