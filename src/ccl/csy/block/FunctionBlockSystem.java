package ccl.csy.block;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import net.bplaced.opl.ccl.CompileSystem;
import net.bplaced.opl.ccl.cat.CclCodeBlock;

import ccl.csy.Parameters;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class FunctionBlockSystem implements CompileSystem<CclCodeBlock, File> {

	private static final Pattern DEF_PATTERN = Pattern.compile
			("\\s*def\\s+([a-zA-Z0-9_]+)\\s*(\\[.+\\])?\\s*", Pattern.DOTALL);
	
	protected static int count = 0;

	protected String name;
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		if(infos.getKeyword() == null) return false;
		if(DEF_PATTERN.matcher(infos.getKeyword()).matches()){
			count++;
			this.name = extractName(infos.getKeyword());
			return true;
		}
		return false;
	}

	private String extractName(String keyword) {
		Matcher m = DEF_PATTERN.matcher(keyword);
		m.matches();
		return m.group(1);
	}

	@Override
	public String compileComplete(CclCodeBlock infos)
			throws ImplementationException, DebugException {
		return Parameters.parseParameters(infos.getCondition()) + "\n" + infos.getContent();
	}

	@Override
	public File getOutput() {
		return new File("__func" + count + "__.cl2");
	}

	@Override
	public String include() {
		String path = getOutput().getPath();
		path = path.substring(0, path.length() - 1) + "0";
		return "reserve " + name + "\nputM " + path + "\nstore";
	}

}
