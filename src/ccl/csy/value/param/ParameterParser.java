package ccl.csy.value.param;

import java.util.ArrayList;
import java.util.List;

import ccl.csy.value.ValueCompiler;

public class ParameterParser {

	public static ParameterParseResult compile(String params) {
		params = params.substring(1, params.length() - 1);
		
		StringBuilder temp = new StringBuilder();
		List<String> list = new ArrayList<String>();
		
		boolean inString = false;
		boolean unescape = false;
		int layer = 0;
		
		for(int i = 0; i < params.length(); i++){
			char c = params.charAt(i);
			temp.append(c);
			if(unescape){
				unescape = false;
				continue;
			}
			if(inString){
				if(c == '"') inString = false;
				continue;
			}
			switch(c){
			case '"':
				inString = true; break;
			case '(':
				layer++; break;
			case ')':
				layer--;
				if(layer <= -1) throw new RuntimeException("Negative layer " + layer);
				break;
			case ',':
				if(layer == 0){
					String str = temp.toString();
					list.add(str.substring(0, str.length() - 1).trim());
					temp = new StringBuilder();
				}
				break;
			}
		}
		
		String str = temp.toString();
		if(!str.isEmpty()){
			list.add(str.trim());
		}
		
		return new ParameterParseResult(compileParsed(list), buildParamValueList(list.size()));
	}

	private static String buildParamValueList(int size) {
		StringBuilder builder = new StringBuilder();
		String cfg = ":#";
		for(int i = 0; i < size; i++){
			builder.append(cfg);
		}
		return builder.toString();
	}

	private static String compileParsed(List<String> list) {
		StringBuilder builder = new StringBuilder();
		for(int i = list.size() - 1; i >= 0; i--){
			ValueCompiler compiler = new ValueCompiler(list.get(i));
			compiler.act();
			builder.append(compiler.get());
			builder.append("\n");
		}
		return builder.toString();
	}

}
