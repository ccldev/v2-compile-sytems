package ccl.csy.value.array;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.value.ValueCompiler;

public class ArrayItem {
	
	private static final Pattern NAMED_ITEM_PATTERN = Pattern.compile
			("\\s*([a-zA-Z][a-zA-Z0-9_]*)\\s*:\\s*(.*)\\s*", Pattern.DOTALL);
	private static final Pattern ITEM_PATTERN = Pattern.compile
			("\\s*()\\s*(.*)\\s*", Pattern.DOTALL);
	
	private int index;
	private Matcher matcher;
	
	private String name;
	private String value;

	public ArrayItem(int index, String item) {
		this.index = index;
		matcher = NAMED_ITEM_PATTERN.matcher(item);
		if(!matcher.find()){
			matcher = ITEM_PATTERN.matcher(item);
			matcher.find();
		}
		
		name = matcher.group(1).trim();
		value = matcher.group(2).trim();
	}

	public String compile() {
		ValueCompiler vc = new ValueCompiler(value);
		vc.act();
		
		StringBuilder b = new StringBuilder("D\nD\n");
		b.append("G:~:set\n");
		b.append("G:I" + index);
		b.append("\nS\n");
		b.append(vc.get());
		b.append("\nS\n(:~:~:~\nP\n");
		if(!name.isEmpty()){
			b.append("G:~:getIndex\n(:#:I" + index + "\n");
			b.append("G:~:setName\n");
			b.append("(:#:S" + name + "\nP\n");
		}else{
			b.append("P\n");
		}
		return b.toString();
	}

}
