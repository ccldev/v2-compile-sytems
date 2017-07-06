package ccl.psy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v2_1.code.CclCodePart;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

import io.github.coalangsoft.cclproject.CompileSystem;
import io.github.coalangsoft.cclproject.cat.CclCodeSnippet;

public class CustomSnippetSystem implements CompileSystem<CclCodeSnippet, File> {

	private Pattern template;
	private String result;

	public CustomSnippetSystem(Pattern template, String result) {
		this.template = template;
		this.result = result;
	}

	@Override
	public boolean accept(CclCodeSnippet infos) {
		return template.matcher(infos.getRaw()).matches();
	}

	@Override
	public String compileComplete(CclCodeSnippet infos)
			throws ImplementationException, DebugException, IOException {
		Matcher m = template.matcher(infos.getRaw());
		m.matches();
		ArrayList<String> groups = new ArrayList<String>();
		for(int i = 0; i < m.groupCount(); i++){
			groups.add(m.group(i + 1));
		}
		String code = String.format(result, groups.toArray());
		return new CclCodePart(code).compile(null);
	}

	@Override
	public File getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String include() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
