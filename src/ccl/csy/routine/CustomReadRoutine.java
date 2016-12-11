package ccl.csy.routine;

public class CustomReadRoutine implements ValueReadRoutine {
	
	@Override
	public boolean process(char c, int next) {
		return next == -1;
	}

}
