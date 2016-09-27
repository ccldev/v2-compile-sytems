package ccl.csy.block;

import ccl.v2.cpl.BlockSurround;

public class NormalBlock extends BlockSurround {

	@Override
	public String getBefore(String keyword, String condition, String identifier) {
		return "{";
	}

	@Override
	public String getAfter(String keyword, String condition, String identifier) {
		return "}";
	}

	@Override
	public void update(long i) {
		
	}
	
}
