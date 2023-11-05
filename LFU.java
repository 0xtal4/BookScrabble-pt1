package test;
import java.util.LinkedHashMap;

public class LFU implements CacheReplacementPolicy{
    LinkedHashMap<String,Integer> hashMap;
    public LFU(){
        hashMap = new LinkedHashMap<>();
    }
    public void add(String word){
        if (hashMap.containsKey(word)){
            hashMap.replace(word,hashMap.get(word)+1);
        }else{
            hashMap.put(word,1);
        }
    }

    public String remove() {
        int[] minValue = {Integer.MAX_VALUE};
        String[] minString = {""};

        hashMap.forEach((k,v) -> {
            if (v <= minValue[0]){
                minValue[0] = v;
                minString[0] = k;
            }
        });
        hashMap.forEach((k,v) -> {
            if (v == minValue[0] && k != minString[0]) {
                minString[0] = hashMap.entrySet().iterator().next().getKey();
            }
        });
        return minString[0];
    }
}
