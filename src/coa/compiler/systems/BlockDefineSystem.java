package coa.compiler.systems;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import io.github.coalangsoft.cclproject.CompileSystem;
import io.github.coalangsoft.cclproject.CompileSystems;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matthias on 13.06.2017.
 */
public class BlockDefineSystem implements CompileSystem<String, Void> {

    private static final Pattern BLOCK_PATTERN = Pattern.compile
            ("\\s*#\\s*block\\s+([^\\s]+)\\s+([^\\s]+)\\s+(.+)\\s*");

    @Override
    public boolean accept(String infos) {
        return BLOCK_PATTERN.matcher(infos).matches();
    }

    @Override
    public String compileComplete(String infos) throws ImplementationException, DebugException, IOException {
        Matcher m = BLOCK_PATTERN.matcher(infos);
        m.matches();

        String keyword = m.group(1);
        String args = m.group(2);
        String replacement = m.group(3);

        CompileSystems.BLOCK.add(new CustomBlockSystem(keyword, args, replacement));
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
