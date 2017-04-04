package ccl.csy.value.compile;

import ccl.csy.array.ArrayItem;
import ccl.csy.todo.compile.ParameterList;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class ArrayValueCompiler implements RawValueCompiler{

	@Override
	public String compileRawValue(String val) throws ImplementationException,
			DebugException {
		StringBuilder builder = new StringBuilder("putA");
		
		val = val.trim();
		val = val.substring(1, val.length() - 1);
		val = val.trim();
		
		if(val.isEmpty()){
			return builder.toString();
		}
		
		ParameterList items = new ParameterList(val);
		if(items.count() > 0){
			builder.append("\n");
		}
		for(int i = 0; i < items.count(); i++){
			ArrayItem item = new ArrayItem(i, items.get(i));
			builder.append(item.compile());
			if(i < items.count() - 1){
				builder.append("\n");
			}
		}
		
		return builder.toString();
	}

}
