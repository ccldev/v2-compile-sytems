package csy.block;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import ccl.v2_1.cat.CclCodeBlock;
import ccl.v2_1.code.CclCodePart;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.err.NI;
import ccl.v2_1.sys.CompileSystem;

public class NormalBlockSystem implements CompileSystem<CclCodeBlock, File>{

	@Override
	public boolean accept(CclCodeBlock infos) {
		return infos.getBefore().isEmpty();
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException, IOException {
		try {
			return "{\n" + CclCodePart.compileAll(infos.getCodePart().buildCodeParts(1)) + "\n}";
		} catch (FileNotFoundException e) {
			throw new DebugException(e);
		}
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
