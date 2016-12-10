package csy;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeSnippet;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

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
		builder.append("load error\n");
		builder.append(StaticValueCompiler.compileValue(m.group(1)));
		builder.append("\ninvoke 1\nret");
		
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
