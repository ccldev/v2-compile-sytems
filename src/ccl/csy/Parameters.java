package ccl.csy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v1.Tool;

public class Parameters {
	
	private static final Pattern ARG_PATTERN = Pattern.compile
			("\\s*([a-zA-Z0-9_]+)\\s*:?(.*)\\s*", Pattern.DOTALL);
	
	public static String parseParameters(String raw) {
		if(raw == null){
			return "";
		}
		
		StringBuilder builder = new StringBuilder("var parameters = @;");
		
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

	private static String singleParameter(int index, String name, String value) {
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
				builder.append("parameters.getOrDefault(");
				builder.append(index);
				builder.append(",");
				builder.append(value);
				builder.append(")");
			}
		}else{
			builder.append("parameters.getOrDefault(");
			builder.append(index);
			builder.append(",undefined)");
		}
		
		builder.append(";\n");
		
		return builder.toString();
		
	}
	
}
