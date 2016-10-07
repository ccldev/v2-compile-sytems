package csy.block;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import ccl.v2_1.cat.CclCodeBlock;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.sys.CompileSystem;

public class NativeBlockSystem implements CompileSystem<CclCodeBlock, File>{
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		return infos.getKeyword().equals("native");
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException, IOException {
		Scanner s = new Scanner(infos.getContent());
		StringBuilder ret = new StringBuilder();
		while(s.hasNextLine()){
			ret.append(s.nextLine().trim());
			ret.append("\n");
		}
		s.close();
		return ret.toString();
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
