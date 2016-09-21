package ccl.csy.value.param;

public class ParameterParseResult {

	private String before;
	private String base;

	public ParameterParseResult(String before, String base) {
		this.before = before;
		this.base = base;
	}

	public String getBefore() {
		return before;
	}

	public String getBase() {
		return base;
	}

}
