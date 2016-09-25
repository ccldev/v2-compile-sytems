package ccl.csy.value.todo;

public enum TodoType {
	
	GET, INVOKE, REFLECT;
	
	private String code;

	public String getCode() {
		return code;
	}
	
	public static TodoType get(char c){
		switch(c){
		case '.': return GET;
		case '(': return INVOKE;
		case '[': return REFLECT;
		default: throw new RuntimeException("Unexpected todo identifier " + c);
		}
	}
	
}
