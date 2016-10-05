package csy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import psy.IncludeSystem;
import psy.OperatorDefineSystem;

import csy.DefaultSystem;
import csy.VariableDeclarationSystem;
import csy.block.FunctionBlockSystem;
import csy.block.IfBlockSystem;
import csy.block.NormalBlockSystem;
import csy.block.WhileBlockSystem;

import ccl.v1.compile.CompileResult;
import ccl.v1.compile.use.CclCompiler;
import ccl.v1.read.Code;
import ccl.v1.read.InputFactory;
import ccl.v2_1.code.CclCode;
import ccl.v2_1.code.CclCodePart;
import ccl.v2_1.compile.Finisher;
import ccl.v2_1.debug.DebugHelper;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.pre.PreProcessor;
import ccl.v2_1.sys.CompileSystems;

public class CCL {

	public static File compile(String name) throws IOException, DebugException, ImplementationException {
		File in = new File(name);
		File cl1 = new File(name.substring(0, name.length() - 1) + "1");
		cl1.createNewFile();
		File cl0 = new File(name.substring(0, name.length() - 1) + "0");

		PrintStream out = new PrintStream(cl1);
		
		String content = preProcess(readFileContent(in));
		
		CclCode code = new CclCode(content);
		CclCodePart[] parts = null;
		try {
			parts = code.buildCodeParts(0);
		}catch(Exception e){
			new DebugHelper(code.getRaw()).consume(e);
		}
		
		for(int i = 0; i < parts.length; i++){
			try {
				out.println(parts[i].compile());
			} catch (Exception e) {
				new DebugHelper(parts[i]).consume(e);
			}
		}
		
		out.close();

		CclCompiler compiler = new CclCompiler();
		CompileResult res = compiler.compile(new Code(InputFactory.file(cl1)));
		res.makeError();

		FileWriter writer = new FileWriter(cl0);
		writer.write(res.getResult());
		writer.close();

		return cl0;
	}

	private static String preProcess(String fileContent) throws DebugException, ImplementationException, IOException {
		PreProcessor processor = new PreProcessor();
		Scanner s = new Scanner(fileContent);
		while(s.hasNextLine()){
			processor.process(s.nextLine());
		}
		return processor.get();
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
	
	public static void initSystems() {
		Finisher.set(new FinisherImpl());
		
		CompileSystems.SNIPPET.add(new VariableDeclarationSystem());
		CompileSystems.SNIPPET.add(new VariableSetSystem());
		CompileSystems.SNIPPET.add(new ReturnCompileSystem());
		CompileSystems.SNIPPET.add(new DefaultSystem());
		
		CompileSystems.BLOCK.add(new NormalBlockSystem());
		CompileSystems.BLOCK.add(new FunctionBlockSystem());
		CompileSystems.BLOCK.add(new IfBlockSystem());
		CompileSystems.BLOCK.add(new WhileBlockSystem());
		
		CompileSystems.PRE.add(new OperatorDefineSystem());
		CompileSystems.PRE.add(new IncludeSystem());
	}

}
