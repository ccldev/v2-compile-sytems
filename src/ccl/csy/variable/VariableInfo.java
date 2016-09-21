package ccl.csy.variable;

import java.util.regex.Matcher;

public class VariableInfo {

	private String name;
	private String value;

	public VariableInfo(Matcher matcher) {
		matcher.find();
		name = matcher.group(1);
		value = matcher.group(2);
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
