package ccl.csy.value.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.value.ValueParser;

public class CCLNumberType extends ValueParser{

	private Pattern INT_PATTERN = Pattern.compile
			("\\s*([0-9]*)\\s*(.*)", Pattern.DOTALL);
	private Pattern FLOAT_PATTERN = Pattern.compile
			("\\s*([0-9]*\\.[0-9])\\s*(.*)", Pattern.DOTALL);
	
	private Matcher matcher;
	
	@Override
	public void feed(String val) {
		matcher = FLOAT_PATTERN.matcher(val);
		if(!matcher.find()){
			matcher = INT_PATTERN.matcher(val);
			matcher.find();
		}
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
