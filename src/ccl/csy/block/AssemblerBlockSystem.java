package ccl.csy.block;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import io.github.coalangsoft.cclproject.CompileSystem;
import io.github.coalangsoft.cclproject.cat.CclCodeBlock;

import java.io.File;
import java.io.IOException;

/**
 * Created by Matthias on 14.06.2017.
 */
public class AssemblerBlockSystem implements CompileSystem<CclCodeBlock, File> {

    @Override
    public boolean accept(CclCodeBlock infos) {
        return infos.getKeyword().equals("asm");
    }

    @Override
    public String compileComplete(CclCodeBlock infos) throws ImplementationException, DebugException, IOException {
        String[] split = infos.getContent().trim().split("\n\\s*");
        return String.join("\n", split) + "\nload undefined";
    }

    @Override
    public File getOutput() {
        return null;
    }

    @Override
    public String include() {
        return null;
    }
}
