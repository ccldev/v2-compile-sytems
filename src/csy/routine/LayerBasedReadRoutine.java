package csy.routine;

import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.layers.LayerState;
import ccl.v2_1.operators.Operators;

public class LayerBasedReadRoutine implements ValueReadRoutine {
	
	private boolean finished;
	private LayerState layerState;
	private int lastLayer;
	
	public LayerBasedReadRoutine(char opener, char closer) throws ImplementationException {
		this.layerState = new LayerState(new char[]{opener}, new char[]{closer});
	}

	@Override
	public boolean process(char c, int next) {
		layerState.feed(c);
		finished = layerState.isBiggest(0) && lastLayer > 0;
		lastLayer = layerState.get(Operators.GREATER_THEN);
		return finished;
	}

}
