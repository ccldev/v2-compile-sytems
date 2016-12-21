package ccl.psy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bplaced.opl.ccl.CompileSystem;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.pre.PreProcessor;

public class IncludeSystem implements CompileSystem<String, Void>{

	private String libPrefix;

	public IncludeSystem(String libPrefix){
		if(!libPrefix.endsWith("/")){
			libPrefix += "/";
		}
		this.libPrefix = libPrefix;
	}
	
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
		
		String fpath = m.group(1);
		if(fpath.startsWith("<") && fpath.endsWith(">")){
			fpath = fpath.substring(1, fpath.length() - 1);
			fpath = libPrefix + "ccl/std/" + fpath + ".cl2";
		}else if(fpath.startsWith("\"") && fpath.endsWith("\"")){
			fpath = fpath.substring(1, fpath.length() - 1);
		}else{
			fpath = libPrefix + fpath;
		}
		
		if(included.contains(fpath.toUpperCase())){
			return "";
		}
		included.add(fpath.toUpperCase());
		
		Scanner s = new Scanner(new File(fpath));
		while(s.hasNextLine()){
			processor.process(s.nextLine());
		}
		s.close();
		
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