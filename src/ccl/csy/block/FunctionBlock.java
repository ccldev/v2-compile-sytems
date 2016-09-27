package ccl.csy.block;

import ccl.v2.cpl.BlockSurround;

public class FunctionBlock extends BlockSurround{

	@Override
	public String getBefore(String keyword, String condition, String identifier) {
		String[] split = condition.split(",");
		StringBuilder b = new StringBuilder("native(){");
		
		if(condition.trim().length() >= 1){
			for(int i = 0; i < split.length; i++){
				b.append("V1::" + split[i].trim() + ":V" + i + "\n");
			}
		}
		
		b.append("}");
		return b.toString();
	}

	@Override
	public String getAfter(String keyword, String condition, String identifier) {
		return "";
	}

	@Override
	public void update(long i) {
		
	}
	
}
