package ccl.csy.value.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.value.ValueParser;

public class CCLFunctionType extends ValueParser{

	private String fname;
	private String todo;
	
	private static final Pattern FUNC_PATTERN = Pattern.compile
			("\\s*([a-zA-Z0-9/]+)\\s*(.*)\\s*", Pattern.DOTALL);

	@Override
	public void feed(String val) {
		Matcher m = FUNC_PATTERN.matcher(val);
		m.find();
		fname = m.group(1) + ".cl0";
		todo = m.group(2);
	}

	@Override
	public String getBase() {
		return fname;
	}

	@Override
	public String getTodo() {
		return todo;
	}

}
