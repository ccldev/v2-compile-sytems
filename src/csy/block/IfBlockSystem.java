package csy.block;

import java.io.File;
import java.io.IOException;

import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;

import csy.SpecialResults;
import csy.StaticValueCompiler;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

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
		
		String condition = StaticValueCompiler.compileValue(infos.getCondition()).trim();
		
		if(condition.equals(SpecialResults.FALSE)){
			return "";
		}
		
		builder.append(condition);
		
		if(!content.isEmpty()){
			counter++;
			
			if(!condition.equals(SpecialResults.TRUE)){
				builder.append("\nif _if_" + counter + "_\n");
				builder.append("goto _if_" + counter + "_end_\n");
				builder.append("mark _if_" + counter + "_\n");
				builder.append("newscope");
			}
			
			//on if
			if(!content.isEmpty()){
				builder.append("\n");
			}
			builder.append(content);
			
			if(!condition.equals(SpecialResults.TRUE)){
				builder.append("\noldscope");
				builder.append("\nmark _if_" + counter + "_end_");
			}else{
				builder.append("\npop");
			}
		}else{
			builder.append("\npop");
		}
		
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
