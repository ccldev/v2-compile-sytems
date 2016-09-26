package ccl.psy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ccl.v2.sys.CompileSystem;

public class IncludeSystem implements CompileSystem{

	private static final Pattern INCLUDE_PATTERN = Pattern.compile
			("include\\s*(.+)", Pattern.DOTALL);
	
	@Override
	public boolean accept(String snippet) {
		return INCLUDE_PATTERN.matcher(snippet).find();
	}

	@Override
	public String compile(String snippet) {
		Matcher m = INCLUDE_PATTERN.matcher(snippet);
		m.find();
		try {
			return readContent(new File(m.group(1)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Unable to load file content!", e);
		}		
	}

	private String readContent(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
		StringBuilder builder = new StringBuilder();
		while(s.hasNextLine()){
			builder.append(s.nextLine());
			builder.append("\n");
		}
		s.close();
		return builder.toString();
	}

}
