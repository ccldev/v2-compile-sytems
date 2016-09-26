package ccl.csy.value;

import ccl.csy.value.todo.TodoCompiler;

public class ValueCompiler {
	
	private String raw;
	private StringBuilder builder;
	
	public ValueCompiler(String value){
		this.raw = value;
		this.builder = new StringBuilder();
	}
	
	public void act(){
		char start = raw.charAt(0);
		ValueType type = null;
		switch(start){
		case '(': type = ValueType.NATIVE; break;
		case '[': type = ValueType.ARRAY; break;
		case '"': type = ValueType.STRING; break;
		case '@': type = ValueType.FUNCTION; break;
		}
		if(Character.isDigit(start) || start == '-') type = ValueType.NUMBER;
		else if(type == null) type = ValueType.VARIABLE;
		
		ValueParser parser = ValueParser.get(type);
		parser.feed(raw);
		builder.append("G:" + type.getCode() + parser.getBase());
		builder.append("\n");
		
		String todo = parser.getTodo();
		if(!todo.trim().isEmpty()){
			builder.append(TodoCompiler.compileTodo(todo.trim()));
		}
	}

	public String get() {
		return builder.toString();
	}
	
}
