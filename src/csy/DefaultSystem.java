package csy;

import java.io.File;

import ccl.v2_1.cat.CclCodeSnippet;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.sys.CompileSystem;

public class DefaultSystem implements CompileSystem<CclCodeSnippet, File>{

	@Override
	public boolean accept(CclCodeSnippet infos) {
		return true;
	}

	@Override
	public String compileComplete(CclCodeSnippet infos)
			throws ImplementationException, DebugException {
		return StaticValueCompiler.compileValue(infos.getRaw().substring(0, infos.getRaw().length() - 1).trim());
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
