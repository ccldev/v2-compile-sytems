package csy.value;

import csy.routine.LayerBasedReadRoutine;
import csy.routine.RegexBasedReadRoutine;
import csy.routine.ValueReadRoutine;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.err.NI;

public class ValueExtracter {

	private ValueType base;
	private ValueReadRoutine routine;

	public ValueExtracter(ValueType type) throws ImplementationException {
		this.base = type;
		this.routine = computeReadRoutine();
	}

	private ValueReadRoutine computeReadRoutine() throws ImplementationException {
		if(base.usesOpenAndClose()){
			return new LayerBasedReadRoutine(base.opener, base.closer);
		}else{
			return new RegexBasedReadRoutine(base.valueInfo);
		}
	}

	public ValueExtract extract(String value) throws DebugException{
		StringBuilder valContent = new StringBuilder();
		for(int i = 0; i < value.length(); i++){
			char c = value.charAt(i);
			valContent.append(c);
			if(routine.process(c, i < value.length() - 1 ? value.charAt(i + 1) : -1)){
				return new ValueExtract(valContent.toString(), value.substring(i + 1), base);
			}
		}
		throw new DebugException("Unable to compute value from input String! Input: \n" + value);
	}

}
