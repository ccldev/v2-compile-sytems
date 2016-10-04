package csy.value.compile;

import csy.array.ArrayItem;
import csy.todo.compile.ParameterList;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.err.NI;

public class ArrayValueCompiler implements RawValueCompiler{

	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		StringBuilder builder = new StringBuilder("G:A0\n");
		
		val = val.trim();
		val = val.substring(1, val.length() - 1);
		val = val.trim();
		
		if(val.isEmpty()){
			return builder.toString();
		}
		
		ParameterList items = new ParameterList(val);
		for(int i = 0; i < items.count(); i++){
			ArrayItem item = new ArrayItem(i, items.get(i));
			builder.append(item.compile());
			builder.append("\n");
		}
		
		return builder.toString();
	}

}
