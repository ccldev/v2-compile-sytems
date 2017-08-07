package ccl.psy;

import ccl.csy.CCL;
import ccl.csy.context.GlobalSettings;
import ccl.v2_1.err.DebugException;
import ccl.v2_1.err.ImplementationException;
import ccl.v2_1.pre.PreProcessor;
import cpa.subos.io.IO;
import cpa.subos.io.IOBase;
import io.github.coalangsoft.cclproject.CompileSystem;

import java.io.IOException;
import java.util.ArrayList;

public class UseSystem implements CompileSystem<String,Void> {
    @Override
    public boolean accept(String infos) {
        return infos.startsWith("#use");
    }

    @Override
    public String compileComplete(String infos) throws ImplementationException, DebugException, IOException {
        String[] arguments = infos.substring(4).split(",");
        String[] result = new String[arguments.length];

        for(int i = 0; i < arguments.length; i++){
            result[i] = process(arguments[i].trim());
        }

        return String.join("\n", result);
    }

    private String process(String trim) throws DebugException, IOException, ImplementationException {
        String[] split = trim.split("\\s+");
        ArrayList<String> part = new ArrayList<String>();
        ArrayList<String> results = new ArrayList<>();

        boolean isSpecial = false;
        for(int i = 0; i < split.length; i++){
            if(part.isEmpty()){
                part.add(split[i]);
                continue;
            }else{
                if(split[i].startsWith(".")){
                    part.add(split[i]);
                }else if(isSpecial){
                    part.add(split[i]);
                    isSpecial = false;
                }else{
                    switch(split[i]){
                        case "from":
                            part.add("!from");
                            isSpecial = true;
                            break;
                        default:
                            results.add(finishPart(part));
                            part.clear();
                            i--;
                    }
                }
            }
        }
        if(!part.isEmpty()){
            results.add(finishPart(part));
        }
        return String.join("\n", results);
    }

    private String finishPart(ArrayList<String> part) throws DebugException, IOException, ImplementationException {
        if(part.size() == 1){
            for(int i = 0; i < GlobalSettings.libFiles.size(); i++){
                String s = GlobalSettings.libFiles.get(i);
                if(s.endsWith(part.get(0) + ".cl2") || s.endsWith(part.get(0) + ".ccl")){
                    PreProcessor p = new PreProcessor();
                    p.process("#include \"" + s + "\"");
                    return p.get();
                }
            }
        }
        throw new RuntimeException("Not able to compile #use " + String.join(" ", part));
    }

    @Override
    public Void getOutput() {
        return null;
    }

    @Override
    public String include() {
        return null;
    }
}
