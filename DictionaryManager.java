package test;


import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    private static DictionaryManager handler = null;
    private HashMap<String,Dictionary> dictionaries = new HashMap<String,Dictionary>();
    public boolean query(String...args){
        boolean flag = false;
        for (int i =0; i<args.length - 1; i++){
            dictionaries.put(args[i],new Dictionary(args[i]));
            if(dictionaries.get(args[i]).query(args[args.length - 1]))
                flag = true;
        }
        return flag;
    }
    public boolean challenge(String...args){
        boolean flag = false;
        for (int i =0; i<args.length - 1; i++){
            dictionaries.put(args[i],new Dictionary(args[i]));
            if(dictionaries.get(args[i]).challenge(args[args.length - 1]))
                flag = true;
        }
        return flag;
    }
    public int getSize(){
        return dictionaries.size();
    }
    public static DictionaryManager get(){
        if(handler == null)
            handler = new DictionaryManager();
        return handler;
    }
}
