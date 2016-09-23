package ccl.csy.value.dotype;

import ccl.csy.value.param.ParameterParseResult;
import ccl.csy.value.param.ParameterParser;
import ccl.csy.value.todo.TodoInfo;

public class CCLInvoke extends TodoInfo{

	private StringBuilder paramBuilder;
	private String todo;
	
	@Override
	public TodoInfo init(String todo) {
		paramBuilder = new StringBuilder();
		
		int layer = 0;
		boolean unescape = false;
		boolean inString = false;
		
		int i;
		loop:
		for(i = 0; i < todo.length(); i++){
			char c = todo.charAt(i);
			paramBuilder.append(c);
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
				if(layer == 0) break loop;
				if(layer <= -1) throw new RuntimeException("Negative layer " + layer);
				break;
			}
		}
		
		this.todo = todo.substring(i + 1);
		return this;
	}

	@Override
	public String getBase() {
		ParameterParseResult res = ParameterParser.compile(paramBuilder.toString());
		return res.getBefore() + "\n;:invoke " + res.getBase();
	}

	@Override
	public String getTodo() {
		return todo;
	}

}
