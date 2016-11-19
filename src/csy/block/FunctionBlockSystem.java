package csy.block;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;

import ccl.v1.Tool;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class FunctionBlockSystem implements CompileSystem<CclCodeBlock, File> {

	private static final Pattern DEF_PATTERN = Pattern.compile
			("\\s*def\\s+([a-zA-Z0-9_]+)\\s*(\\[.+\\])?\\s*", Pattern.DOTALL);
	private static final Pattern ARG_PATTERN = Pattern.compile
			("\\s*([a-zA-Z0-9_]+)\\s*:?(.*)\\s*", Pattern.DOTALL);
	
	protected static int count = 0;

	protected String name;
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		if(infos.getKeyword() == null) return false;
		if(DEF_PATTERN.matcher(infos.getKeyword()).matches()){
			count++;
			this.name = extractName(infos.getKeyword());
			if(infos.getAfterCondition().length() > 0){
				System.err.println(infos.getAfterCondition());
			}
			return true;
		}
		return false;
	}

	private String extractName(String keyword) {
		Matcher m = DEF_PATTERN.matcher(keyword);
		m.matches();
		return m.group(1);
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException {
		return parseParameters(infos.getCondition()) + "\n" + infos.getContent();
	}

	protected String parseParameters(String raw) {
		StringBuilder builder = new StringBuilder();
		
		if(raw.trim().length() == 0) return "";
		String[] params = Tool.split(raw.trim(), ',');
		for(int i = 0; i < params.length; i++){
			Matcher m = ARG_PATTERN.matcher(params[i].trim());
			m.matches();
			
			builder.append(singleParameter(i, m.group(1), m.group(2)));
			builder.append("\n");
		}
		
		return builder.toString();
	}

	private String singleParameter(int index, String name, String value) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("var ");
		builder.append(name);
		builder.append("=");
		
		value = value.trim();
		if(!value.isEmpty()){
			if(value.equals("...")){
				builder.append("parameters.cut(");
				builder.append(index);
				builder.append(")");
			}else{
				builder.append(value);
			}
		}else{
			builder.append("undefined");
		}
		
		builder.append(";\n");
		
		if(!value.equals("...")){
			builder.append("if(");
			builder.append(index);
			builder.append(".lss(parameters.length())){");
			
			builder.append(name);
			builder.append("=parameters.get(");
			builder.append(index);
			builder.append(");");
			
			builder.append("}");
		}
		
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
		return "reserve " + name + "\nputM " + path + "\nstore";
	}

}
