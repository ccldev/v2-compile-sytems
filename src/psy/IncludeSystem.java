package psy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.pre.PreProcessor;
import ccl.v2_1.sys.CompileSystem;

public class IncludeSystem implements CompileSystem<String, Void>{

	private ArrayList<String> included = new ArrayList<String>();
	
	private static final Pattern INCLUDE_PATTERN = Pattern.compile
			("\\s*#\\s*include\\s+(.+)\\s*");
	
	@Override
	public boolean accept(String infos) {
		return INCLUDE_PATTERN.matcher(infos).matches();
	}

	@Override
	public String compileComplete(String infos) throws ImplementationException,
			DebugException, IOException {
		Matcher m = INCLUDE_PATTERN.matcher(infos);
		m.matches();
		
		PreProcessor processor = new PreProcessor();
		
		if(included.contains(m.group(1))) return "";
		included.add(m.group(1));
		
		Scanner s = new Scanner(new File(m.group(1)));
		while(s.hasNextLine()){
			processor.process(s.nextLine());
		}
		
		return processor.get();
	}

	@Override
	public Void getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String include() {
		// TODO Auto-generated method stub
		return null;
	}

}
