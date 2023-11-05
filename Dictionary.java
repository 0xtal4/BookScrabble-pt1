package test;


import java.util.ArrayList;

public class Dictionary {
    CacheManager Exists;
    CacheManager notExists;
    BloomFilter filter;
    ArrayList<String> files;

    public Dictionary(String...fileNames){
        Exists = new CacheManager(400, new LRU());
        notExists = new CacheManager(100, new LFU());
        filter = new BloomFilter(256, "SHA1","MD5");
        files = new ArrayList<>();
        for (String file : fileNames) {
            files.add(file);
            ArrayList<String> fileWords = IOSearcher.scan(file);
            for(String w : fileWords){
                filter.add(w);
            }
        }

    }
    public boolean query(String w){
        if (Exists.query(w)){
            return true;
        } else if (notExists.query(w)) {
            return false;
        } else {
            if (filter.contains(w)){
                Exists.add(w);
                return true;
            }else {
                notExists.add(w);
                return false;
            }
        }
    }
    public boolean challenge(String w){
        try {
            for (String file : files) {
                if (IOSearcher.search(w,file)) {
                    Exists.add(w);
                    return true;
                }
            }
            notExists.add(w);
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
