package ccl.csy.block;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.csy.Parameters;
import io.github.coalangsoft.cclproject.CompileSystem;
import io.github.coalangsoft.cclproject.cat.CclCodeBlock;

import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class NormalBlockSystem implements CompileSystem<CclCodeBlock, File>{

	private int count = 0;

	public static final Pattern FUNC_LITERAL_PATTERN = Pattern.compile("\\|([^\\|]*)\\|(.*)", Pattern.DOTALL);
	private boolean parameters;

	@Override
	public boolean accept(CclCodeBlock infos) {
		return infos.getBefore().isEmpty();
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException, IOException {
		this.parameters = false;

		count++;
		Matcher m = FUNC_LITERAL_PATTERN.matcher(infos.getContent());

		if(m.matches()){
			this.parameters = true;
			return Parameters.parseParameters(m.group(1)) + m.group(2);
		}

		return infos.getContent();
	}

	@Override
	public File getOutput() {
		return new File("_nblock_" + count + "_.cl2");
	}

	@Override
	public String include() {
		String res = "putM _nblock_" + count + "_.cl0";
		if(!parameters){
			return res + "\nduplicate\n__invoke0\nnnr";
		}
		return res;
	}

}
