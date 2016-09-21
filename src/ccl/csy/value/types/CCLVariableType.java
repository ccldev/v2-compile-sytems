package ccl.csy.value.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.value.ValueParser;

public class CCLVariableType extends ValueParser{

	private static final Pattern VAR_PATTERN = Pattern.compile
			("\\s*([a-zA-Z][a-zA-Z0-9_]*)\\s*(.*)", Pattern.DOTALL);
	
	private Matcher matcher;
	
	@Override
	public void feed(String val) {
		matcher = VAR_PATTERN.matcher(val);
		matcher.find();
	}

	@Override
	public String getBase() {
		return "V" + matcher.group(1);
	}

	@Override
	public String getTodo() {
		return matcher.group(2);
	}

}
