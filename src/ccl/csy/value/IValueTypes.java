package ccl.csy.value;

import ccl.csy.value.compile.ArrayValueCompiler;
import ccl.csy.value.compile.FuncLiteralValueCompiler;
import ccl.csy.value.compile.NumberValueCompiler;
import ccl.csy.value.compile.RegexValueCompiler;
import ccl.csy.value.compile.StringValueCompiler;
import ccl.csy.value.compile.UnboundValueCompiler;
import ccl.csy.value.compile.VariableValueCompiler;

public interface IValueTypes {
	
	ValueType ARRAY = new ValueType('[', ']', new ArrayValueCompiler());
	ValueType NUMBER = new ValueType("\\d", "\\d+", new NumberValueCompiler());
	ValueType STRING = new ValueType('"', new StringValueCompiler());
	ValueType VARIABLE = new ValueType("[a-zA-Z0-9_]", "[a-zA-Z0-9_]+", new VariableValueCompiler());
	ValueType REGEX = new ValueType('/', new RegexValueCompiler());
	ValueType FUNC_LITERAL = new ValueType('<', '>', new FuncLiteralValueCompiler());
	ValueType UNBOUND = new ValueType("\\.", "\\." + VARIABLE.valueInfo, new UnboundValueCompiler());
	
}
