package ccl.csy.value.compile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import ccl.csy.Parameters;
import ccl.v1.Tool;
import ccl.v2_1.compile.Finisher;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class FuncLiteralValueCompiler implements RawValueCompiler {

	private int ctr;
	
	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		String base = val.substring(1,val.length() - 1);
		String[] split = Tool.split(base, '|');
		
		File out = new File("_anon" + (ctr++) + "_.cl2");
		try {
			PrintWriter w = new PrintWriter(out);
			w.print(Parameters.parseParameters(split[0]));
			w.print("return " + split[1] + ";");
			w.close();
		} catch (FileNotFoundException e) {
			throw new DebugException(e);
		}
		
		try {
			return Finisher.finish(out);
		} catch (IOException e) {
			throw new DebugException(e);
		}
	}

}
