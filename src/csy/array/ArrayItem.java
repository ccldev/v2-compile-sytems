package csy.array;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import csy.StaticValueCompiler;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class ArrayItem {

	private static final Pattern ITEM_PATTERN = Pattern.compile
			("()\\s*(.+)\\s*", Pattern.DOTALL);
	private static final Pattern NAMED_PATTERN = Pattern.compile
			("\\s*([a-zA-Z0-9]+)\\s*:\\s*(.+)\\s*", Pattern.DOTALL);
	
	private int index;
	private String raw;
	private String name;
	private String value;

	public ArrayItem(int index, String raw) {
		this.index = index;
		this.raw = raw;
	}

	public int getIndex() {
		return index;
	}

	public String getRaw() {
		return raw;
	}

	public String compile() throws DebugException, ImplementationException {
		analyze();
		StringBuilder builder = new StringBuilder();
		
		builder.append("D\nG:~:push\n");
		builder.append(StaticValueCompiler.compileValue(value));
		if(!name.isEmpty()){
			builder.append("\nS\nG:S" + name);
			builder.append("\nS\n;:invoke 2\nP");
		}else{
			builder.append("\nS\n;:invoke 1\nP");
		}
		return builder.toString();
	}

	private void analyze() {
		Matcher m = NAMED_PATTERN.matcher(raw);
		if(!m.matches()){
			m = ITEM_PATTERN.matcher(raw);
			m.matches();
		}
		this.name = m.group(1);
		this.value = m.group(2);
	}

}
