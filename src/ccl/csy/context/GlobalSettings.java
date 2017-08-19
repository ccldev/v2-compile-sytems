package ccl.csy.context;

import cpa.subos.io.IO;
import cpa.subos.io.IOBase;
import io.github.coalangsoft.cclproject.opt.Instruction;
import io.github.coalangsoft.cclproject.opt.InstructionOptimizer;
import io.github.coalangsoft.cclproject.opt.InstructionReader;
import io.github.coalangsoft.cclproject.opt.SystemChange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class GlobalSettings {

    public static ArrayList<String> usedVariables = new ArrayList<String>();
    public static ArrayList<String> changedVariables = new ArrayList<String>();
    public static ArrayList<String> outputFiles = new ArrayList<String>();
    public static ArrayList<String> libFiles = new ArrayList<String>();
    public static ArrayList<String> builtinVariables = new ArrayList<>();

    public static void optimize(boolean optimizeVariables) throws IOException {
        //Optimize
        SystemChange change = new SystemChange();
        change.modifyAll(changedVariables);

        System.out.println(change);

        for(int i = 0; i < outputFiles.size(); i++){
            IOBase<?> cl0 = IO.file(outputFiles.get(i));

            ArrayList<Instruction> is = InstructionReader.read(cl0.reader());
            InstructionOptimizer.DEFAULT.optimize(optimizeVariables, change, is);
            cl0 = IO.buffer();

            OutputStream stream;
            PrintStream out = new PrintStream(stream = cl0.writer());
            for(int k = 0; k < is.size(); k++){
                out.println(is.get(k).getRaw());
            }
            stream.close();
            IO.file(outputFiles.get(i)).downloadFrom(cl0);

            System.out.println(outputFiles.get(i));
        }
    }

}
