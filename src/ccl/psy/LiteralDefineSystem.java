package ccl.psy;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import ccl.csy.value.ValueType;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import io.github.coalangsoft.cclproject.CompileSystem;

public class LiteralDefineSystem implements CompileSystem<String, Void>{

	private static final Pattern LITDEF_PATTERN = Pattern.compile
			("\\s*#\\s*literal\\s+([^\\s]+)\\s+(.+)\\s*");
	
	@Override
	public boolean accept(String infos) {
		return LITDEF_PATTERN.matcher(infos).matches();
	}

	@Override
	public String compileComplete(String infos) throws ImplementationException,
			DebugException, IOException {
		Matcher m = LITDEF_PATTERN.matcher(infos);
		m.matches();
		String result = m.group(2);
		String identifier = m.group(1);
		if(identifier.length() == 1){
			ValueType.register(new ValueType(
				identifier.charAt(0), new BasicLiteralCompiler(result)
			));
			return "";
		}else if(identifier.length() == 2){
			ValueType.register(new ValueType(
					identifier.charAt(0), identifier.charAt(1),
					new BasicLiteralCompiler(result)
				));
				return "";
		}else{
			char opener = identifier.charAt(0);
			char closer = identifier.charAt(identifier.length() - 1);
			ValueType.register(new ValueType(
					opener, closer,
					new ComplexLiteralCompiler(identifier, result)
			));
			return "";
		}
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
