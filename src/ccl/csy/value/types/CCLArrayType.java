package ccl.csy.value.types;

import java.util.ArrayList;

import ccl.csy.value.ValueParser;
import ccl.csy.value.array.ArrayItem;

public class CCLArrayType extends ValueParser{

	private ArrayList<String> list = new ArrayList<String>();
	
	@Override
	public void feed(String val) {
		list.clear();
		val = val.trim();
		val = val.substring(1, val.length() - 1);
		boolean inString = false;
		boolean unescape = false;
		int layer = 0;
		
		StringBuilder temp = new StringBuilder();
		int i;
		loop:
		for(i = 1; i < val.length(); i++){
			char c = val.charAt(i);
			temp.append(c);
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
				if(layer <= -1) throw new RuntimeException("Negative layer " + layer);
				break;
			case ',':
				if(layer == 0){
					String str = temp.toString();
					list.add(str.substring(0, str.length() - 1).trim());
					temp = new StringBuilder();
				}
				break;
			}
		}
		String str = temp.toString();
		if(!str.isEmpty()){
			list.add(str.trim());
		}
	}

	@Override
	public String getBase() {
		StringBuilder builder = new StringBuilder(list.size() + "\n");
		for(int i = 0; i < list.size(); i++){
			builder.append(compileItem(i, list.get(i)));
			builder.append("\n");
		}
		return builder.toString();
	}

	private String compileItem(int index, String item) {
		ArrayItem i = new ArrayItem(index, item);
		return i.compile();
	}

	@Override
	public String getTodo() {
		return "";
	}

}
