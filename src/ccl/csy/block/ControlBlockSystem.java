package ccl.csy.block;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ccl.csy.StaticValueCompiler;
import io.github.coalangsoft.cclproject.CompileSystem;
import io.github.coalangsoft.cclproject.cat.CclCodeBlock;
import ccl.csy.StaticTodoCompiler;
import ccl.v2_1.compile.Finisher;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class ControlBlockSystem implements CompileSystem<CclCodeBlock, File>{
	
	private int count = 0;
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		if(infos.getKeyword() != null){
			count++;
			return true;
		}
		return false;
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException, IOException {
		File func = new File("_b" + count + "_.cl2");
		FileWriter w = new FileWriter(func);
		w.write(infos.getContent());
		w.close();
		
		count++;
		
		StringBuilder res = new StringBuilder();
		res.append(StaticValueCompiler.compileValue(infos.getKeyword()));
		res.append("\n");
		res.append(StaticTodoCompiler.compileTodo("(" + infos.getCondition() + ")"));
		res.append(Finisher.finish(func));
		res.append("\ninvoke 1");
		
		return res.toString().trim();
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
