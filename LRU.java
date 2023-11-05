package test;
import java.util.*;

public class LRU implements CacheReplacementPolicy{
    LinkedHashSet<String> usedQueue;
    public LRU(){
        usedQueue = new LinkedHashSet<String>();
        }

    public void add(String word){
                if (usedQueue.contains(word)){ // if the queue contains the word, move it to the end.
                    usedQueue.remove(word);
                    usedQueue.add(word);
                }else {
                    usedQueue.add(word);
                }
    }

    public String remove() {
       return usedQueue.toArray(new String[1])[0]; // first word is the least recently added, get deleted.
    }
}
