package csy.value.compile;

import ccl.v2_1.err.NI;

public class NativeValueCompiler implements RawValueCompiler{

	@Override
	public String compileRawValue(String val) {
		val = val.trim();
		val = val.substring(1, val.length() - 1);
		val = val.replaceAll("\\s", "");
		return "G:N" + val;
	}

}
