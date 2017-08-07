package ccl.csy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import ccl.csy.context.GlobalSettings;
import ccl.psy.*;
import ccl.csy.block.AssemblerBlockSystem;
import cpa.subos.io.BufferIOBase;
import cpa.subos.io.IO;
import cpa.subos.io.IOBase;
import cpa.subos.io.file.FileIOBase;
import io.github.coalangsoft.cclproject.opt.Instruction;
import io.github.coalangsoft.cclproject.opt.InstructionOptimizer;
import io.github.coalangsoft.cclproject.opt.InstructionReader;
import io.github.coalangsoft.cclproject.opt.SystemChange;
import io.github.coalangsoft.lib.log.TimeLogger;
import io.github.coalangsoft.lib.reflect.CustomClassFinder;
import io.github.coalangsoft.cclproject.CompileSystems;

import ccl.csy.block.ControlBlockSystem;
import ccl.csy.block.NormalBlockSystem;
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
		GlobalSettings.outputFiles.add(out.getName());

		IOBase<?> missingVariables = IO.buffer();

		for(int i = 0; i < GlobalSettings.usedVariables.size(); i++){
			if(!GlobalSettings.changedVariables.contains(GlobalSettings.usedVariables.get(i)) && !GlobalSettings.builtinVariables.contains(GlobalSettings.usedVariables.get(i))){
				try {
					PreProcessor p = new PreProcessor();
					p.process("#use " + GlobalSettings.usedVariables.get(i));
					missingVariables.writeString(p.get());
				}catch(Exception e){
					TimeLogger.err.log("Warning: Variable used but not set: " + GlobalSettings.usedVariables.get(i));
				}
			}
		}

		String libraries = missingVariables.buildString().trim();
		if(!libraries.isEmpty()){
			IOBase<?> compiledLibs = compile(false, IO.string(libraries));
			IOBase<?> newOut = IO.buffer();
			newOut.downloadFrom(compiledLibs);
			newOut.downloadFrom(out);
			out.downloadFrom(newOut);
		}

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
		IO.file(libPrefix).listFilesDeep().forEach((f) -> {
			String name = f.getPath();
			if(name.endsWith(".cl2") || name.endsWith(".ccl")){
				if(!f.getName().startsWith("_")){
					GlobalSettings.libFiles.add(name);
				}
			}
			return null;
		});

		Finisher.set(new FinisherImpl(head));
		
		CompileSystems.SNIPPET.add(new EmptyVariableDeclarationSystem());
		CompileSystems.SNIPPET.add(new VariableDeclarationSystem());
		CompileSystems.SNIPPET.add(new VariableSetSystem());
		CompileSystems.SNIPPET.add(new ReturnCompileSystem());
		CompileSystems.SNIPPET.add(new ThrowSystem());
		
		CompileSystems.SNIPPET.setDefault(new DefaultSystem());
		
		CompileSystems.BLOCK.add(new NormalBlockSystem());
		CompileSystems.BLOCK.add(new AssemblerBlockSystem());

		CompileSystems.BLOCK.setDefault(new ControlBlockSystem());

		CompileSystems.PRE.add(new IncludeSystem(libPrefix));
		CompileSystems.PRE.add(new LiteralDefineSystem());
		CompileSystems.PRE.add(new SnippetSystem());
		CompileSystems.PRE.add(new AliasSystem());
		CompileSystems.PRE.add(new BlockDefineSystem());
		CompileSystems.PRE.add(new AssemblerIncludeSystem());
		CompileSystems.PRE.add(new UseSystem());
		CompileSystems.PRE.add(new BuiltinSystem());
	}

}
