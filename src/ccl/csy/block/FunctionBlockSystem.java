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
			("\\s*def\\s+([a-zA-Z0-9_]+)\\s*(extends\\s+[A-Za-z][A-Za-z0-9_]*)?\\s*", Pattern.DOTALL);
	
	protected static int count = 0;

	protected String name;
	private String extend;
	
	@Override
	public boolean accept(CclCodeBlock infos) {
		if(infos.getKeyword() == null) return false;
		if(DEF_PATTERN.matcher(infos.getKeyword()).matches()){
			count++;
			this.name = extractName(infos.getKeyword());
			this.extend = null;
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
		Pattern p = Pattern.compile("\\s*extends\\s+([A-Za-z][A-Za-z0-9_]*)\\s*");
		Matcher m = p.matcher(infos.getAfterCondition());
		if(m.matches()){
			this.extend = m.group(1);
		}
		
		return Parameters.parseParameters(infos.getCondition()) + "\n"
			+ infos.getContent();
	}

	@Override
	public File getOutput() {
		return new File("__" + name + "_" + count + "__.cl2");
	}

	@Override
	public String include() {
		String path = getOutput().getPath();
		path = path.substring(0, path.length() - 1) + "0";
		
		StringBuilder b = new StringBuilder();
		
		b.append("reserve " + name + "\n");
		
		if(extend != null){
			b.append("load " + extend);
			b.append("\nget extend\n");
		}
		
		b.append("putM " + path + "\n");
		
		if(extend != null){
			b.append("invoke 1\n");
		}
		
		b.append("store");
		return b.toString();
	}

}
