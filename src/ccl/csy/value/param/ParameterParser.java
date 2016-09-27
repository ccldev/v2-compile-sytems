package ccl.csy.value.param;

import java.util.ArrayList;
import java.util.List;

import ccl.csy.value.ValueCompiler;
import ccl.v1.read.InputFactory;
import ccl.v2.layer.LayerExit;
import ccl.v2.layer.LayerSystem;

public class ParameterParser {

	public static ParameterParseResult compile(String params) {
		params = params.substring(1, params.length() - 1);
		
		LayerSystem sys = new LayerSystem(new char[]{'(', '['}, new char[]{')', ']'}, true, LayerExit.EXIT_ON_END);
		
		sys.feed(InputFactory.string(params));
		List<String> list = sys.getList();
		
		if(list.size() == 1){
			if(list.get(0).isEmpty()) list.clear();
		}
		
		return new ParameterParseResult(compileParsed(list), list.size() + "");
	}

	private static String compileParsed(List<String> list) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < list.size(); i++){
			ValueCompiler compiler = new ValueCompiler(list.get(i));
			compiler.act();
			builder.append(compiler.get());
			builder.append("\nS");
			builder.append("\n");
		}
		return builder.toString();
	}

}
