package psy;

import java.io.IOException;

import csy.block.NativeBlockSystem;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.CompileSystems;

public class UnsafeSystem implements CompileSystem<String, Void>{

	@Override
	public boolean accept(String infos) {
		return infos.equals("#unsafe");
	}

	@Override
	public String compileComplete(String infos) throws ImplementationException,
			DebugException, IOException {
		CompileSystems.BLOCK.add(new NativeBlockSystem());
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
