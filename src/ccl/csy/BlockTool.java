package ccl.csy;

import java.io.IOException;

import io.github.coalangsoft.cclproject.cat.CclCodeBlock;
import ccl.v2_1.code.CclCodePart;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;

public class BlockTool {
	
	public static String elseContent(CclCodeBlock beforeElse) throws DebugException, ImplementationException, IOException{
		CclCodePart f;
		if((f = beforeElse.getFollowing()) != null){
			if(f.isBlock()){
				CclCodeBlock b = new CclCodeBlock(f, null);
				if("else".equals(b.getKeyword())){
					return b.compileContent().trim();
				}
			}
		}
		return null;
	}
	
}
