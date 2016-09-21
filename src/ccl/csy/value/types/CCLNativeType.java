package ccl.csy.value.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.value.ValueParser;

public class CCLNativeType extends ValueParser{

	public static final Pattern NATIVE_PATTERN = Pattern.compile
			("\\s*\\(\\s*([a-zA-Z0-9\\.]+)\\s*\\)\\s*(.*)\\s*", Pattern.DOTALL);
	
	private Matcher matcher;
	
	@Override
	public void feed(String val) {
		matcher = NATIVE_PATTERN.matcher(val);
		matcher.find();
	}

	@Override
	public String getBase() {
		return matcher.group(1);
	}

	@Override
	public String getTodo() {
		return matcher.group(2);
	}

}
