package ccl.csy.block;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;
import ccl.csy.StaticTodoCompiler;
import ccl.csy.StaticValueCompiler;
import ccl.v2_1.compile.Finisher;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.err.NI;

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
		
		StringBuilder res = new StringBuilder();
		res.append(Finisher.finish(func));
		res.append("\nget ");
		res.append(infos.getKeyword());
		res.append("\n");
		res.append(StaticTodoCompiler.compileTodo("(" + infos.getCondition() + ")"));
		res.append("pop");
		
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
