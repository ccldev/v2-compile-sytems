package ccl.csy;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.context.GlobalSettings;
import io.github.coalangsoft.cclproject.CompileSystem;
import io.github.coalangsoft.cclproject.cat.CclCodeSnippet;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

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
		
		if(m.group(2).trim().equals("undefined")){
			return "__mkvar_u " + m.group(1);
//			return "reserve " + m.group(1) + "\npop";
		}

		if(!GlobalSettings.changedVariables.contains(m.group(1))){
			GlobalSettings.changedVariables.add(m.group(1));
		}

		String base = StaticValueCompiler.compileValue(m.group(2)) +
				"\n__mkvar " + m.group(1);
		return base;
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
