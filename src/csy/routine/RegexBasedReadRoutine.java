package csy.routine;

import java.util.regex.Pattern;

public class RegexBasedReadRoutine implements ValueReadRoutine{

	private String regex;
	private StringBuilder content = new StringBuilder();
	private boolean lastMatched;
	private String lastString;
	private boolean ret;

	public RegexBasedReadRoutine(String regex) {
		this.regex = regex;
	}

	@Override
	public boolean process(char c, int next) {
		if(content.toString().isEmpty()){
			content.append(c);
			boolean matches = Pattern.matches(regex, content.toString());
			if(next != -1){
				boolean test = Pattern.matches(regex, content.toString() + (char) next);
				if(matches && !test){
					return true;
				}
			}
		}
		if(next == -1){
			return Pattern.matches(regex, content.toString());
		}
		content.append((char) next);
		boolean matches = Pattern.matches(regex, content.toString());
		ret = !(matches) && lastMatched;
		lastMatched = matches;
		return ret;
	}

}
