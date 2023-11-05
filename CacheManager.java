package test;
import java.util.HashSet;

public class CacheManager {
	HashSet<String> hashSet;
	int maxSize;
    CacheReplacementPolicy policy;

    public CacheManager(int size, CacheReplacementPolicy plc) {
        hashSet = new HashSet<String>();
        maxSize = size;
        policy = plc;
    }
    void add(String word){
        if (hashSet.size() == maxSize){
            hashSet.remove(policy.remove());
            if (policy.getClass().getName() == "test.LRU")
            policy.add(word);
            hashSet.add(word);
        }else {
            if (policy.getClass().getName() == "test.LRU")
            policy.add(word);
            hashSet.add(word);
        }
    }
    boolean query(String word){
        if (policy.getClass().getName() == "test.LFU"){
            policy.add(word);
        }
        if (hashSet.contains(word)){
            return true;
        }
        return false;
    }
}
