package ccl.psy;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import cpa.subos.io.IO;
import io.github.coalangsoft.cclproject.CompileSystem;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matthias on 14.06.2017.
 */
public class AssemblerIncludeSystem implements CompileSystem<String, Void> {

    private static final Pattern ASM_PATTERN = Pattern.compile
            ("\\s*#\\s*asm\\s+(.+)\\s*");

    @Override
    public boolean accept(String infos) {
        return ASM_PATTERN.matcher(infos).matches();
    }

    @Override
    public String compileComplete(String infos) throws ImplementationException, DebugException, IOException {
        Matcher m = ASM_PATTERN.matcher(infos);
        m.matches();
        return "asm{" + IO.file(m.group(1)).buildString("UTF-8") + "};";
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
