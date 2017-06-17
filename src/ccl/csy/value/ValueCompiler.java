package ccl.csy.value;

import ccl.csy.StaticTodoCompiler;
import ccl.v2_1.code.CclCodePart;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import io.github.coalangsoft.cclproject.cat.CclCodeBlock;

import java.io.IOException;

public class ValueCompiler {

	private String raw;
	private String blockValue;
	private ValueExtract result;

	public ValueCompiler(String value) {
		this.raw = value.trim();
	}

	public void act() throws DebugException, ImplementationException {
		if(raw.length() == 0) throw new DebugException("A value may not be empty!");

		if(raw.endsWith("}")){
			try {
				CclCodeBlock block = new CclCodeBlock(new CclCodePart(raw), null);
				blockValue = block.compile();
				return;
			} catch (IOException e) {
				throw new DebugException(e);
			}
		}
		ValueType type = ValueType.compute(raw);
		ValueExtracter extracter = type.getExtracter();
		result = extracter.extract(raw);
	}

	public String get() throws ImplementationException, DebugException {
		if(blockValue != null) {
			return blockValue;
		}else{
			String todo = StaticTodoCompiler.compileTodo(result.getTodo()).trim();
			return result.getCompiler().compileRawValue(result.getVal()) + (todo.isEmpty() ? "" : "\n") +
					todo;
		}
	}

}
