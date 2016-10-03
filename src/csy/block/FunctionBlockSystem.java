package csy.block;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v2_1.cat.CclCodeBlock;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.err.NI;
import ccl.v2_1.sys.CompileSystem;

public class FunctionBlockSystem implements CompileSystem<CclCodeBlock, File> {

	private static final Pattern DEF_PATTERN = Pattern.compile
			("\\s*def\\s+([a-zA-Z0-9_]+)");
	
	private static int count = 0;

	private String name;
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		if(DEF_PATTERN.matcher(infos.getKeyword()).matches()){
			count++;
			this.name = extractName(infos.getKeyword());
			return true;
		}
		return false;
	}

	private String extractName(String keyword) {
		Matcher m = Pattern.compile("\\s*def\\s+([a-zA-Z0-9_]+)\\s*").matcher(keyword);
		m.matches();
		return m.group(1);
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException {
		return parseParameters(infos.getCondition()) + "\n" + infos.getContent();
	}

	private String parseParameters(String raw) {
		StringBuilder builder = new StringBuilder();
		
		if(raw.trim().length() == 0) return "";
		String[] params = raw.split(",");
		for(int i = 0; i < params.length; i++){
			builder.append(singleParameter(i, params[i].trim()));
			builder.append("\n");
		}
		
		return builder.toString();
	}

	private String singleParameter(int index, String name) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("var ");
		builder.append(name);
		builder.append("=undefined;\n");
		
		builder.append("if(");
		builder.append(index);
		builder.append(".lss(parameters.length())){");
		
		builder.append(name);
		builder.append("=parameters.get(");
		builder.append(index);
		builder.append(").getValue();");
		
		builder.append("}");
		
		return builder.toString();
		
	}

	@Override
	public File getOutput() {
		return new File("__func" + count + "__.cl2");
	}

	@Override
	public String include() {
		String path = getOutput().getPath();
		path = path.substring(0, path.length() - 1) + "0";
		return "V1::" + name + ":M" + path;
	}

}
