package ccl.csy.value.dotype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.value.todo.TodoInfo;

public class CCLGet extends TodoInfo{

	private static final Pattern GET_PATTERN = Pattern.compile
			("\\s*\\.\\s*([a-zA-Z][a-zA-Z0-9_]*)\\s*(.*)", Pattern.DOTALL);
	
	private Matcher matcher;
	
	@Override
	public TodoInfo init(String todo) {
		matcher = GET_PATTERN.matcher(todo);
		matcher.find();
		return this;
	}

	@Override
	public String getTodo() {
		return matcher.group(2);
	}

	@Override
	public String getBase() {
		return "G:~:" + matcher.group(1);
	}

}
