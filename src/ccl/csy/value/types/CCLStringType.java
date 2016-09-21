package ccl.csy.value.types;

import ccl.csy.value.ValueParser;

public class CCLStringType extends ValueParser{

	private String base;
	private String todo;

	@Override
	public void feed(String val) {
		StringBuilder str = new StringBuilder();
		
		boolean unescape = false;
		int i;
		
		loop:
		for(i = 1; i < val.length(); i++){
			char c = val.charAt(i);
			str.append(c);
			if(unescape){
				unescape = false;
				continue;
			}
			switch(c){
			case '\\': unescape = true; break;
			case '"': break loop;
			}
		}
		
		this.base = str.toString();
		this.base = base.substring(0, base.length() - 1);
		this.todo = val.substring(i + 1);
	}

	@Override
	public String getBase() {
		return base;
	}

	@Override
	public String getTodo() {
		return todo;
	}
	
	
	
}
