package ccl.psy;

import ccl.csy.CCL;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import cpa.subos.io.IO;
import io.github.coalangsoft.cclproject.CompileSystem;
import io.github.coalangsoft.cclproject.cat.CclCodeBlock;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matthias on 13.06.2017.
 */
public class CustomBlockSystem implements CompileSystem<CclCodeBlock, File> {

    private final Pattern keyword;
    private final Pattern args;
    private final String replacement;

    public CustomBlockSystem(String keyword, String args, String replacement) {
        this.keyword = Pattern.compile(keyword);
        this.args = Pattern.compile(args);
        this.replacement = replacement;
    }

    @Override
    public boolean accept(CclCodeBlock infos) {
        if(!keyword.matcher(infos.getKeyword()).matches()){
            return false;
        }if(!args.matcher(infos.getCondition()).matches()){
            return false;
        }
        return true;
    }

    @Override
    public String compileComplete(CclCodeBlock infos) throws ImplementationException, DebugException, IOException {
        Matcher keywordMatcher = keyword.matcher(infos.getKeyword());
        Matcher argsMatcher = args.matcher(infos.getCondition());

        keywordMatcher.matches();
        argsMatcher.matches();

        Object[] formatData = new Object[keywordMatcher.groupCount() + argsMatcher.groupCount() + 1];
        for(int i = 0; i < keywordMatcher.groupCount(); i++){
            formatData[i] = keywordMatcher.group(i+1);
        }
        for(int i = 0; i < argsMatcher.groupCount(); i++){
            formatData[i + keywordMatcher.groupCount()] = argsMatcher.group(i+1);
        }
        formatData[formatData.length - 1] = infos.getContent();

        return CCL.compile(false, IO.string(
                String.format(replacement, formatData)
        )).buildString("UTF-8").trim() + "\nload undefined";
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
