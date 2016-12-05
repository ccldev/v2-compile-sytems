package csy.block;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class NativeBlockSystem implements CompileSystem<CclCodeBlock, File>{
	
	private static final Pattern INSTR_PATTERN = Pattern.compile
			("\\s*([a-zA-Z0-9]+)\\s*(.*)");
	
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
			Matcher m = INSTR_PATTERN.matcher(s.nextLine());
			if(m.matches()){
				ret.append(m.group(1) + " " + m.group(2));
				ret.append("\n");
			}
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
