package ccl.csy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import net.bplaced.opl.ccl.CompileSystems;

import ccl.csy.VariableDeclarationSystem;
import ccl.csy.block.ControlBlockSystem;
import ccl.csy.block.ElseStub;
import ccl.csy.block.FunctionBlockSystem;
import ccl.csy.block.IfBlockSystem;
import ccl.csy.block.NormalBlockSystem;
import ccl.csy.block.WhileBlockSystem;
import ccl.psy.IncludeSystem;
import ccl.psy.LiteralDefineSystem;
import ccl.psy.OperatorDefineSystem;
import ccl.psy.SnippetSystem;
import ccl.v2_1.code.CclCode;
import ccl.v2_1.code.CclCodePart;
import ccl.v2_1.compile.Finisher;
import ccl.v2_1.debug.DebugHelper;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.pre.PreProcessor;

public class CCL {
	
	public static File compile(boolean head, String name) throws IOException, DebugException, ImplementationException {
		File in = new File(name);
		File cl0 = new File(name.substring(0, name.length() - 1) + "0");
		cl0.createNewFile();
		
		PrintStream out = new PrintStream(cl0);
		
		String content = preProcess(head, readFileContent(in));
		
		CclCode code = new CclCode(content);
		CclCodePart[] parts = null;
		try {
			parts = code.buildCodeParts(0);
		}catch(Exception e){
			new DebugHelper(code.getRaw()).consume(e);
		}
		
		out.println("sPut " + name);
		
		for(int i = 0; i < parts.length; i++){
			try {
				String c = parts[i].compile(i + 1 < parts.length ? parts[i+1] : null);
				if(c.trim().isEmpty()){
					continue;
				}
				out.println(c);
			} catch (Exception e) {
				new DebugHelper(parts[i]).consume(e);
			}
		}
		
		out.println("sPop");
		
		out.close();
		
		return cl0;
	}

	private static String preProcess(boolean header, String fileContent) throws DebugException, ImplementationException, IOException {
		PreProcessor processor = new PreProcessor();
		
		String completeCode = fileContent;
		if(header){
			completeCode = readContent(get("/ccl/res/header.cl2")) + "\n" + completeCode;
		}
		
		Scanner s = new Scanner(completeCode);
		while(s.hasNextLine()){
			processor.process(s.nextLine());
		}
		s.close();
		return processor.get();
	}

	private static InputStream get(String res) {
		return CCL.class.getResourceAsStream(res);
	}

	private static String readFileContent(File f) throws FileNotFoundException {
		Scanner s = new Scanner(f);
		StringBuilder builder = new StringBuilder();
		while(s.hasNextLine()){
			builder.append(s.nextLine());
			builder.append("\n");
		}
		s.close();
		return builder.toString();
	}
	
	private static String readContent(InputStream stream){
		Scanner s = new Scanner(stream);
		StringBuilder builder = new StringBuilder();
		while(s.hasNextLine()){
			builder.append(s.nextLine());
			builder.append("\n");
		}
		s.close();
		return builder.toString();
	}
	
	public static void initSystems(boolean head, String libPrefix) {
		Finisher.set(new FinisherImpl(head));
		
		CompileSystems.SNIPPET.add(new EmptyVariableDeclarationSystem());
		CompileSystems.SNIPPET.add(new VariableDeclarationSystem());
		CompileSystems.SNIPPET.add(new ArrayVariableDeclarationSystem());
		CompileSystems.SNIPPET.add(new TupleVariableDeclarationSystem());
		CompileSystems.SNIPPET.add(new VariableSetSystem());
		CompileSystems.SNIPPET.add(new ReturnCompileSystem());
		CompileSystems.SNIPPET.add(new ThrowSystem());
		
		CompileSystems.SNIPPET.setDefault(new DefaultSystem());
		
		CompileSystems.BLOCK.add(new NormalBlockSystem());
		CompileSystems.BLOCK.add(new FunctionBlockSystem());
		CompileSystems.BLOCK.add(new WhileBlockSystem());
		CompileSystems.BLOCK.add(new IfBlockSystem());
		CompileSystems.BLOCK.add(new ElseStub());
		
		CompileSystems.BLOCK.setDefault(new ControlBlockSystem());
		
		CompileSystems.PRE.add(new OperatorDefineSystem());
		CompileSystems.PRE.add(new IncludeSystem(libPrefix));
		CompileSystems.PRE.add(new LiteralDefineSystem());
		CompileSystems.PRE.add(new SnippetSystem());
	}

}
