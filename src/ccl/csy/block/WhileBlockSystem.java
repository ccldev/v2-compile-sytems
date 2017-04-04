package ccl.csy.block;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;

import ccl.v2_1.compile.Finisher;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class WhileBlockSystem implements CompileSystem<CclCodeBlock, File>{
	
	private static int counter = 0;
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		if(infos.getKeyword() == null) return false;
		if(infos.getKeyword().equals("while")){
			return true;
		}
		return false;
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException, IOException {
		
		File func = new File("_w" + counter + "_.cl2");
		FileWriter w = new FileWriter(func);
		w.write(infos.getContent());
		w.close();
		
		StringBuilder res = new StringBuilder();
		res.append("load while\n");
		
		File cnd = new File("_lc" + counter + "_.cl2");
		w = new FileWriter(cnd);
		w.write("return " + infos.getCondition() + ";");
		w.close();
		
		counter++;
		
		res.append(Finisher.finish(cnd));
		res.append("\ninvoke 1\n");
		res.append(Finisher.finish(func));
		res.append("\ninvoke 1\nnnr");
		
		return res.toString();
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
