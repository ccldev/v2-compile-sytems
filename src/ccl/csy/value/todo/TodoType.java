package ccl.csy.value.todo;

public enum TodoType {
	
	GET, INVOKE;
	
	private String code;

	public String getCode() {
		return code;
	}
	
	public static TodoType get(char c){
		switch(c){
		case '.': return GET;
		case '(': return INVOKE;
		default: throw new RuntimeException("Unexpected todo identifier " + c);
		}
	}
	
}
