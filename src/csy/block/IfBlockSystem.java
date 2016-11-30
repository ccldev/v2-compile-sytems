package csy.block;

import java.io.File;
import java.io.IOException;

import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;

import csy.BlockTool;
import csy.SpecialResults;
import csy.StaticValueCompiler;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class IfBlockSystem implements CompileSystem<CclCodeBlock, File> {

	private static int counter = 0;
	private String elseContent;
	
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
		
		elseContent = 	BlockTool.elseContent(infos);
		if(elseContent != null){
			elseContent = 	"newscope\n" + 
					elseContent + 
					"\noldscope";
		}
		
		StringBuilder builder = new StringBuilder();
		
		String content = infos.compileContent().trim();
		
		String condition = StaticValueCompiler.compileValue(infos.getCondition()).trim();
		
		if(condition.equals(SpecialResults.FALSE)){
			return elseContent == null ? "" : elseContent;
		}
		
		if(!content.isEmpty()){
			counter++;
			
			builder.append(condition);
			
			if(!condition.equals(SpecialResults.TRUE)){
				builder.append("\nif _if_" + counter + "_");
				builder.append(elseContent == null ? "" : "\n" + elseContent);
				builder.append("\ngoto _if_" + counter + "_end_\n");
				builder.append("mark _if_" + counter + "_");
			}
			builder.append("\nnewscope");
			
			//on if
			if(!content.isEmpty()){
				builder.append("\n");
				builder.append(content);
			}
			
			builder.append("\noldscope");
			if(!condition.equals(SpecialResults.TRUE)){
				builder.append("\nmark _if_" + counter + "_end_");
			}
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
