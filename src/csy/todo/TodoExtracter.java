package csy.todo;

import csy.routine.CustomReadRoutine;
import csy.routine.LayerBasedReadRoutine;
import csy.routine.RegexBasedReadRoutine;
import csy.routine.ValueReadRoutine;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class TodoExtracter {

	private TodoType base;
	private ValueReadRoutine routine;

	public TodoExtracter(TodoType type) throws ImplementationException {
		this.base = type;
		this.routine = computeReadRoutine();
	}

	private ValueReadRoutine computeReadRoutine() throws ImplementationException {
		if(base == TodoType.CUSTOM){
			return new CustomReadRoutine();
		}
		if(base.usesOpenAndClose()){
			return new LayerBasedReadRoutine(base.opener, base.closer);
		}else{
			return new RegexBasedReadRoutine(base.valueInfo);
		}
	}

	public TodoExtract extract(String value) throws DebugException{
		if(base == TodoType.EMPTY){
			return new TodoExtract("", "", base);
		}
		StringBuilder valContent = new StringBuilder();
		for(int i = 0; i < value.length(); i++){
			char c = value.charAt(i);
			valContent.append(c);
			if(routine.process(c, i < value.length() - 1 ? value.charAt(i + 1) : -1)){
				return new TodoExtract(valContent.toString(), value.substring(i + 1), base);
			}
		}
		throw new DebugException("Unable to compute todo from input String! Input: \n" + value);
	}

}
