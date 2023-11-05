package test;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.BitSet;
import java.lang.Math;

public class BloomFilter {
	BitSet bitArr;
    int Length;
    MessageDigest[] hashFuncs;
    public BloomFilter(int bitLen, String...Algorithms){
        bitArr = new BitSet();
        Length = bitLen;
        hashFuncs = new MessageDigest[Algorithms.length];
        for (int i = 0; i < Algorithms.length;i++){
            try {
                hashFuncs[i] = MessageDigest.getInstance(Algorithms[i]);
            }catch (Exception e){
                System.out.println("function does not exist");
            }
        }
    }
    void add(String w){
        int[] indexes = Calculate(w);
        for (int i : indexes){
            bitArr.set(i);
        }
    }
    Boolean contains(String w){
        int[] indexes = Calculate(w);
        for (int i : indexes){
            if (!bitArr.get(i))
                return false;
        }
        return true;
    }

    public String toString(){
        String bits = new String();
        for (int i = 0; i < bitArr.length(); i++){
            if (bitArr.get(i)) {
                bits += "1";
            }else {
                bits += "0";
            }
        }
        return bits;
    }
    private int[] Calculate(String w){
        BigInteger[] values = new BigInteger[hashFuncs.length];
        int[] indexes = new int[hashFuncs.length];
        for (int i = 0; i < hashFuncs.length; i++){
            values[i] =new BigInteger(hashFuncs[i].digest(w.getBytes()));
            indexes[i] = Math.abs(values[i].intValue()) % Length;
        }
        return indexes;
    }
}
