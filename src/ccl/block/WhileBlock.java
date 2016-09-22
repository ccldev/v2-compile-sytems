package ccl.block;

import ccl.csy.value.ValueCompiler;
import ccl.v2.cat.CodeBlock;
import ccl.v2.cpl.BlockSurround;

public class WhileBlock extends BlockSurround{

	private long i;

	@Override
	public void update(long i){
		this.i = i;
	}
	
	@Override
	public String getBefore(String keyword, String condition) {
		ValueCompiler vc = new ValueCompiler(condition);
		vc.act();
		return "#:while_skip" + i + ":goto\n" + 
				"#:while_base" + i + ":mark\n{";
	}

	@Override
	public String getAfter(String keyword, String condition) {
		ValueCompiler vc = new ValueCompiler(condition);
		vc.act();
		return "}\n#:while_skip" + i + ":mark\n" + vc.get() + "\n?:~" + 
				"\n#:while_base" + i + ":goto";
	}

}
