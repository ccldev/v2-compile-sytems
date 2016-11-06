package csy.block;

import java.io.File;
import java.io.IOException;

import csy.StaticValueCompiler;

import ccl.v2_1.cat.CclCodeBlock;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.sys.CompileSystem;

public class WhileBlockSystem implements CompileSystem<CclCodeBlock, File>{
	
	private static int counter = 0;
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		if(infos.getKeyword() == null) return false;
		if(infos.getKeyword().equals("while")){
			counter++;
			return true;
		}
		return false;
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException, IOException {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("#:_while_" + counter + "_start:mark\n");
		builder.append(StaticValueCompiler.compileValue(infos.getCondition().trim()));
		builder.append("\n?:_while_" + counter + "_continue:goto");
		builder.append("\n#:_while_" + counter + "_end:goto");
		builder.append("\n#:_while_" + counter + "_continue:mark\n{\n");
		builder.append(infos.compileContent());
		builder.append("\n}\n#:_while_" + counter + "_start:goto");
		builder.append("\n#:_while_" + counter + "_end:mark");
		
		return builder.toString();
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
