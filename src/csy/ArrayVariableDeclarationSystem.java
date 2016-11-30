package csy;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeSnippet;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class ArrayVariableDeclarationSystem implements CompileSystem<CclCodeSnippet, File>{
	
	private static final Pattern VAR_PATTERN = Pattern.compile
			("\\s*var\\s+\\[([^=]+)\\]\\s*=\\s*(.+)\\s*;", Pattern.DOTALL);
	private static final Pattern PART_PATTERN = Pattern.compile
			("!?\\s*([a-zA-Z_][a-zA-Z0-9_]*)\\s*(:\\s*[a-zA-Z_][a-zA-Z0-9_]*)?", Pattern.DOTALL);
	
	@Override
	public boolean accept(CclCodeSnippet infos) {
		return VAR_PATTERN.matcher(infos.getRaw()).matches();
	}

	@Override
	public String compileComplete(CclCodeSnippet infos)
			throws ImplementationException, DebugException {
		Matcher m = VAR_PATTERN.matcher(infos.getRaw());
		m.matches();
		
		StringBuilder b = new StringBuilder();
		
		String val = StaticValueCompiler.compileValue(m.group(2));
		b.append(val);
		
		String[] splitted = m.group(1).split(",");
		for(int i = 0; i < splitted.length; i++){
			b.append("\n");
			b.append(process(splitted[i].trim()));
		}
		
		b.append("\npop");
		
		return b.toString();
	}

	private String process(String s) {
		Matcher m = PART_PATTERN.matcher(s);
		m.matches();
		
		StringBuilder b = new StringBuilder();
		
		if(!s.startsWith("!")){
			b.append("reserve ");
			b.append(m.group(1));
			b.append("\npop\n");
		}
		
		b.append("duplicate\n");
		b.append("get ");
		
		if(m.group(2) == null){
			b.append(m.group(1));
		}else{
			b.append(m.group(2).substring(1).trim());
		}
		
		b.append("\nload ");
		b.append(m.group(1));
		b.append("\nstore1");
		
		return b.toString();
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
