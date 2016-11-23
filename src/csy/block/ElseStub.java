package csy.block;

import java.io.File;
import java.io.IOException;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;

public class ElseStub implements CompileSystem<CclCodeBlock, File>{

	@Override
	public boolean accept(CclCodeBlock infos) {
		return infos.getKeyword().equals("else");
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException, IOException {
		return "";
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
