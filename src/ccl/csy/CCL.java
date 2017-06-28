package ccl.csy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import ccl.psy.AliasSystem;
import coa.compiler.systems.AssemblerBlockSystem;
import coa.compiler.systems.AssemblerIncludeSystem;
import coa.compiler.systems.BlockDefineSystem;
import cpa.subos.io.BufferIOBase;
import cpa.subos.io.IO;
import cpa.subos.io.IOBase;
import cpa.subos.io.file.FileIOBase;
import io.github.coalangsoft.lib.reflect.CustomClassFinder;
import io.github.coalangsoft.cclproject.CompileSystems;

import ccl.csy.block.ControlBlockSystem;
import ccl.csy.block.IfBlockSystem;
import ccl.csy.block.NormalBlockSystem;
import ccl.csy.block.WhileBlockSystem;
import ccl.psy.IncludeSystem;
import ccl.psy.LiteralDefineSystem;
import ccl.psy.SnippetSystem;
import ccl.v2_1.code.CclCode;
import ccl.v2_1.code.CclCodePart;
import ccl.v2_1.compile.Finisher;
import ccl.v2_1.debug.DebugHelper;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.pre.PreProcessor;

public class CCL {

	public static CustomClassFinder classFinder;

	public static FileIOBase compile(boolean head, String fname) throws DebugException, IOException, ImplementationException {
		BufferIOBase rawResult = compile(head, IO.file(fname));
		FileIOBase out = IO.file(fname.substring(0, fname.length() - 1) + '0');
		out.downloadFrom(rawResult);
		return out;
	}

	public static BufferIOBase compile(boolean head, IOBase<?> in) throws IOException, DebugException, ImplementationException {
		BufferIOBase cl0 = IO.buffer();

		PrintStream out = new PrintStream(cl0.writer());

		String content = preProcess(head, in.buildString());

		CclCode code = new CclCode(content);
		CclCodePart[] parts = null;
		try {
			parts = code.buildCodeParts(0);
		}catch(Exception e){
			new DebugHelper(code.getRaw()).consume(e);
		}

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

		String result = processor.get();
		return processor.insertSemicolons(result);
	}

	private static InputStream get(String res) {
		return CCL.class.getResourceAsStream(res);
	}

//	private static String readFileContent(File f) throws FileNotFoundException {
//		Scanner s = new Scanner(f);
//		StringBuilder builder = new StringBuilder();
//		while(s.hasNextLine()){
//			builder.append(s.nextLine());
//			builder.append("\n");
//		}
//		s.close();
//		return builder.toString();
//	}
	
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
		CompileSystems.SNIPPET.add(new VariableSetSystem());
		CompileSystems.SNIPPET.add(new ReturnCompileSystem());
		CompileSystems.SNIPPET.add(new ThrowSystem());
		
		CompileSystems.SNIPPET.setDefault(new DefaultSystem());
		
		CompileSystems.BLOCK.add(new NormalBlockSystem());
		CompileSystems.BLOCK.add(new WhileBlockSystem());
		CompileSystems.BLOCK.add(new IfBlockSystem());
		CompileSystems.BLOCK.add(new AssemblerBlockSystem());

		CompileSystems.BLOCK.setDefault(new ControlBlockSystem());

		CompileSystems.PRE.add(new IncludeSystem(libPrefix));
		CompileSystems.PRE.add(new LiteralDefineSystem());
		CompileSystems.PRE.add(new SnippetSystem());
		CompileSystems.PRE.add(new AliasSystem());
		CompileSystems.PRE.add(new BlockDefineSystem());
		CompileSystems.PRE.add(new AssemblerIncludeSystem());
	}

}
