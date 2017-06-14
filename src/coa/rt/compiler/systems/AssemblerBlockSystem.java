package coa.rt.compiler.systems;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
