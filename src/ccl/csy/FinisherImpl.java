package ccl.csy;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import ccl.v2_1.compile.CclCompileResult;
import ccl.v2_1.compile.IFinisher;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class FinisherImpl implements IFinisher{
	
	private boolean head;
	
	public FinisherImpl(boolean head){
		this.head = head;
	}
	
	public String finish(CclCompileResult<File> res) throws IOException, DebugException, ImplementationException{
		if(res.getOutput() != null){
			PrintStream stream = new PrintStream(res.getOutput());
			stream.println(res.getResult());
			stream.close();
			
			CCL.compile(head, res.getOutput().getPath());
			
			return res.getInclude();
		}else{
			return res.getResult();
		}
	}
	
	@Override
	public String finish(File method) throws IOException, DebugException, ImplementationException{
		return "putM " + CCL.compile(head, method.getPath()).getPath();
	}
	
}
