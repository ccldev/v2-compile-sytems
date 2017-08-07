package ccl.psy;

import ccl.csy.context.GlobalSettings;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import io.github.coalangsoft.cclproject.CompileSystem;

import java.io.IOException;

public class BuiltinSystem implements CompileSystem<String,Void> {
    @Override
    public boolean accept(String infos) {
        return infos.startsWith("#builtin");
    }

    @Override
    public String compileComplete(String infos) throws ImplementationException, DebugException, IOException {
        GlobalSettings.builtinVariables.add(infos.substring("#builtin".length()).trim());
        return "";
    }

    @Override
    public Void getOutput() {
        return null;
    }

    @Override
    public String include() {
        return null;
    }
}
