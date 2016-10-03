package csy.todo.compile;

import java.util.ArrayList;
import java.util.List;

import csy.Constants;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.err.NI;
import ccl.v2_1.layers.LayerSystem;

public class ParameterList {

	private ArrayList<String> items = new ArrayList<String>();
	
	public ParameterList(String val) throws ImplementationException, DebugException {
		LayerSystem sys = new LayerSystem(Constants.OPENERS, Constants.CLOSERS, ',');
		sys.setBreakOnZero(false);
		sys.feed(val);
		List<String> list = sys.getList();
		for(int i = 0; i < list.size() - 1; i++){
			String param = list.get(i);
			param = param.substring(0, param.length() - 1);
			items.add(param.trim());
		}
		if(list.size() > 0){
			items.add(list.get(list.size() - 1).trim());
		}
	}

	public int count() {
		return items.size();
	}

	public String get(int i) {
		return items.get(i);
	}

}
