package ccl.csy;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.context.Alias;
import ccl.csy.context.GlobalSettings;
import io.github.coalangsoft.cclproject.CompileSystem;
import io.github.coalangsoft.cclproject.cat.CclCodeSnippet;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class VariableSetSystem implements CompileSystem<CclCodeSnippet, File>{

	private static final Pattern SET_PATTERN = Pattern.compile
			("\\s*([a-zA-Z0-9_]*)\\s*=\\s*(.+)\\s*;\\s*", Pattern.DOTALL);
	
	@Override
	public boolean accept(CclCodeSnippet infos) {
		return SET_PATTERN.matcher(infos.getRaw()).matches();
	}

	@Override
	public String compileComplete(CclCodeSnippet infos)
			throws ImplementationException, DebugException, IOException {
		Matcher m = SET_PATTERN.matcher(infos.getRaw());
		m.matches();

		String name;

		String alias = Alias.lookup(name = m.group(1));
		if(alias != null){
			name = alias;
		}

		if(!GlobalSettings.changedVariables.contains(m.group(1))){
			GlobalSettings.changedVariables.add(m.group(1));
		}

		return 	StaticValueCompiler.compileValue(m.group(2)) +
				"\n__setvar " + m.group(1);
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
