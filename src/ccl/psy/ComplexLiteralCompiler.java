package ccl.psy;

import ccl.csy.StaticValueCompiler;
import ccl.csy.value.compile.RawValueCompiler;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matthias on 17.06.2017.
 */
public class ComplexLiteralCompiler implements RawValueCompiler {

    private final Pattern pattern;
    private final String result;

    public ComplexLiteralCompiler(String identifier, String result) {
        this.pattern = Pattern.compile(identifier, Pattern.DOTALL);
        this.result = result;
    }

    @Override
    public String compileRawValue(String val) throws ImplementationException, DebugException {
        Matcher m = pattern.matcher(val);
        m.matches();

        String[] groups = new String[m.groupCount()];
        for(int i = 0; i < groups.length; i++){
            groups[i] = m.group(i + 1);
        }
        return StaticValueCompiler.compileValue(String.format(result, (Object[]) groups));
    }
}
