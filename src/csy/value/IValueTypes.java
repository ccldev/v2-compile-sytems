package csy.value;

import csy.value.compile.ArrayValueCompiler;
import csy.value.compile.FuncLiteralValueCompiler;
import csy.value.compile.NumberValueCompiler;
import csy.value.compile.RegexValueCompiler;
import csy.value.compile.StringValueCompiler;
import csy.value.compile.VariableValueCompiler;

public interface IValueTypes {
	
	ValueType ARRAY = new ValueType('[', ']', new ArrayValueCompiler());
	ValueType NUMBER = new ValueType("\\d", "\\d+", new NumberValueCompiler());
	ValueType STRING = new ValueType('"', new StringValueCompiler());
	ValueType VARIABLE = new ValueType("[a-zA-Z0-9_]", "[a-zA-Z0-9_]+", new VariableValueCompiler());
	ValueType REGEX = new ValueType('/', new RegexValueCompiler());
	ValueType FUNC_LITERAL = new ValueType('<', '>', new FuncLiteralValueCompiler());
	
}
