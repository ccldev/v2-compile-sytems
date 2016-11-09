package csy.block;

import java.io.File;
import java.io.IOException;

import csy.StaticValueCompiler;

import ccl.v2_1.cat.CclCodeBlock;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.sys.CompileSystem;

public class IfBlockSystem implements CompileSystem<CclCodeBlock, File> {

	private static int counter = 0;
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		if(infos.getKeyword() == null) return false;
		if(infos.getKeyword().equals("if")){
			return true;
		}
		return false;
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException, IOException {
		StringBuilder builder = new StringBuilder();
		
		String content = infos.compileContent().trim();
		
		counter++;
		
		builder.append(StaticValueCompiler.compileValue(infos.getCondition()));
		builder.append("\nif _if_" + counter + "_\n");
		builder.append("goto _if_" + counter + "_end_\n");
		builder.append("mark _if_" + counter + "_");
		
		//on if
		if(!content.isEmpty()){
			builder.append("\n");
		}
		builder.append(content);
		
		builder.append("\nmark _if_" + counter + "_end_");
		
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
