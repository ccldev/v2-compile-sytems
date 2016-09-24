package ccl.csy.value;

public enum ValueType {
	
	NATIVE("N"), ARRAY("A"), STRING("S"),
	NUMBER("F"), VARIABLE(""), FUNCTION("M");
	
	private String code;

	private ValueType(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
