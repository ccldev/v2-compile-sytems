package ccl.csy.block;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ccl.csy.Constants;
import ccl.csy.SpecialResults;
import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;


import ccl.csy.BlockTool;
import ccl.csy.StaticValueCompiler;
import ccl.v2_1.compile.Finisher;
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

		String condition = StaticValueCompiler.compileValue(infos.getCondition()).trim();

		elseContent = 	BlockTool.elseContent(infos);
		if(condition.equals(SpecialResults.FALSE)){
			if(elseContent == null){
				return "";
			}else{
				return "newscope\n" + elseContent + "\noldscope";
			}
		}
		
		File onIf = new File("_i" + counter + "_.cl2");
		FileWriter w = new FileWriter(onIf);
		w.write(infos.getContent());
		w.close();
		
		File onElse = null;
		if(elseContent != null){
			onElse = new File("_e" + counter + "_.cl0");
			w = new FileWriter(onElse);
			w.write(elseContent);
			w.close();
		}
		
		counter++;
		
		StringBuilder ret = new StringBuilder();
		ret.append("load if\n");
		ret.append(StaticValueCompiler.compileValue(infos.getCondition()));
		ret.append("\ninvoke 1\n");
		ret.append(Finisher.finish(onIf));
		ret.append("\n");
		if(onElse == null){
			ret.append("load undefined");
		}else{
			ret.append("putM " + onElse.getName());
		}
		ret.append("\ninvoke 2\nnnr");
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
