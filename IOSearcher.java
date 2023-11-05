package test;
import java.util.ArrayList;
import java.io.File;  // Import the File class
import java.util.Scanner; // Import the Scanner class to read text files

public class IOSearcher {
public static boolean search(String w,String...files) {
    boolean flag = false;

    for (String file : files){
        File current = new File(file);
        try {
            Scanner myReader = new Scanner(current);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(" ");
               for (String word : data){
                   if (word.equals(w))
                       flag = true;
               }

            }
        }catch(Exception e){
            System.out.println("file does not exist");
        }
    }
    return flag;
}
    public static ArrayList<String> scan(String...files){
        ArrayList<String> words = new ArrayList<>();
        for (String file : files){
            File current = new File(file);
            try {
                Scanner myReader = new Scanner(current);
                while (myReader.hasNextLine()) {
                    String[] data = myReader.nextLine().split(" ");
                    for (String word : data){
                        words.add(word);
                    }

                }
            }catch(Exception e){
                System.out.println("file does not exist");
            }
        }
        return words;
    }
}
