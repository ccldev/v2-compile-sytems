package csy;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v2_1.cat.CclCodeSnippet;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.sys.CompileSystem;

public class VariableDeclarationSystem implements CompileSystem<CclCodeSnippet, File>{

	private static final Pattern VAR_PATTERN = Pattern.compile
			("\\s*var\\s+([a-zA-Z0-9_]+)\\s*=\\s*(.+)\\s*;", Pattern.DOTALL);
	
	@Override
	public boolean accept(CclCodeSnippet infos) {
		return VAR_PATTERN.matcher(infos.getRaw()).matches();
	}

	@Override
	public String compileComplete(CclCodeSnippet infos)
			throws ImplementationException, DebugException {
		Matcher m = VAR_PATTERN.matcher(infos.getRaw());
		m.matches();
		return StaticValueCompiler.compileValue(m.group(2)) + "\nV1::" + m.group(1) + ":#";
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
