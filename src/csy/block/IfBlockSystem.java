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
		
		String content = infos.compileContent();
		
		counter++;
		
		builder.append(StaticValueCompiler.compileValue(infos.getCondition()));
		builder.append("\n?:~\n");
		builder.append("#:_if_" + counter + "_:goto\n");
		builder.append("#:_if_" + counter + "_end_:goto\n");
		
		builder.append("#:_if_" + counter + "_:mark\n");
		
		//on if
		builder.append(content);
		
		builder.append("\n#:_if_" + counter + "_end_:mark\n");
		
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
