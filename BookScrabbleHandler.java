package test;
import test.DictionaryManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Arrays;
public class BookScrabbleHandler implements ClientHandler{
 private DictionaryManager DM = new DictionaryManager();
 public void handleClient(InputStream inFromclient, OutputStream outToClient){
     String text = new Scanner(inFromclient).next();

     String[] textArr = text.split(",");
     String[] args = Arrays.copyOfRange(textArr, 1, textArr.length);

     if (textArr[0].equals("Q")){
         if(DM.query(args)){
             try {
                 outToClient.write("true\n".getBytes());
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }else {
             try {
                 outToClient.write("false\n".getBytes());
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
     }else if (textArr[0].equals("C")){
         if(DM.challenge(args)){
             try {
                 outToClient.write("true\n".getBytes());
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }else {
             try {
                 outToClient.write("false\n".getBytes());
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
     }
 }
 public void close(){

 }
}
