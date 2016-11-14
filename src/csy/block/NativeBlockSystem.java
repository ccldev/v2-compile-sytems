package csy.block;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class NativeBlockSystem implements CompileSystem<CclCodeBlock, File>{
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		if(infos.getKeyword() == null) return false;
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
		return ret.toString().trim();
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
