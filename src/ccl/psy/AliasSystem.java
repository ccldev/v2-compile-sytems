package ccl.psy;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.context.Alias;
import io.github.coalangsoft.cclproject.CompileSystem;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class AliasSystem implements CompileSystem<String, Void> {

    private static final Pattern ALIAS_PATTERN = Pattern.compile
            ("\\s*#\\s*alias\\s+([^\\s]+)\\s+(.+)\\s*");

    @Override
    public boolean accept(String infos) {
        return ALIAS_PATTERN.matcher(infos).matches();
    }

    @Override
    public String compileComplete(String infos) throws ImplementationException,
            DebugException, IOException {
        Matcher m = ALIAS_PATTERN.matcher(infos);
        m.matches();
        Alias.add(m.group(1), m.group(2));
        return "";
    }

    @Override
    public Void getOutput() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String include() {
        // TODO Auto-generated method stub
        return null;
    }
}