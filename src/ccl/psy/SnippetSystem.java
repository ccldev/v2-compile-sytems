package ccl.psy;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import ccl.psy.custom.CustomSnippetSystem;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.CompileSystems;

public class SnippetSystem implements CompileSystem<String, Void>{

	private static final Pattern SNIPDEF_PATTERN = Pattern.compile
		("\\s*#\\s*snippet\\s+([^\\s]*)\\s+(.+)\\s*");
	
	@Override
	public boolean accept(String infos) {
		return SNIPDEF_PATTERN.matcher(infos).matches();
	}

	@Override
	public String compileComplete(String infos) throws ImplementationException,
			DebugException, IOException {
		Matcher m = SNIPDEF_PATTERN.matcher(infos);
		m.matches();
		Pattern p = Pattern.compile("\\s*" + m.group(1) + "\\s*;", Pattern.DOTALL);
		CompileSystems.SNIPPET.add(new CustomSnippetSystem(p, m.group(2)));
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
