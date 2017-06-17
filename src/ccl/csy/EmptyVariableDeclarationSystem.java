package ccl.csy;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

import io.github.coalangsoft.cclproject.CompileSystem;
import io.github.coalangsoft.cclproject.cat.CclCodeSnippet;

public class EmptyVariableDeclarationSystem implements CompileSystem<CclCodeSnippet, File>{

	private static final Pattern VAR_PATTERN = Pattern.compile
			("\\s*var\\s+([a-zA-Z0-9_\\s,]+)\\s*;", Pattern.DOTALL);
	
	@Override
	public boolean accept(CclCodeSnippet infos) {
		return VAR_PATTERN.matcher(infos.getRaw()).matches();
	}

	@Override
	public String compileComplete(CclCodeSnippet infos)
			throws ImplementationException, DebugException {
		Matcher m = VAR_PATTERN.matcher(infos.getRaw());
		m.matches();
		
		String[] split = m.group(1).split(",");
		
		StringBuilder b = new StringBuilder();
		
		for(int i = 0; i < split.length; i++){
			b.append("reserve " + split[i].trim() + "\npop");
			if(i < split.length - 1){
				b.append("\n");
			}
		}
		
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
