package test;
import test.Word;
import test.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

/**
 * The type Board.
 */
public class Board {
    /**
     * The Words.
     */
    Word[] words;
    /**
     * The Board.
     */
    Tile[][] board = new Tile[15][15];
    /**
     * The Board legal.
     */
    Bonus[][] bonuses;
    enum Bonus{
        R,MA2,MA3,ML2,ML3,Star
    }
    private static Board b = null;
    private Board(){
        words = new Word[0];
        for(int i = 0; i< 15; i++){
            for(int j = 0; j < 15;j++){
                board[i][j] = null;
            }
        }
        bonuses = new Bonus[15][15];

        // Set Up Bonuses
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                bonuses[i][j] = Bonus.R;
            }
        }
        bonuses[0][0] = Bonus.MA3;
        bonuses[0][3] = Bonus.ML2;
        bonuses[0][7] = Bonus.MA3;
        bonuses[0][11] = Bonus.ML2;
        bonuses[0][14] = Bonus.MA3;
        bonuses[1][1] = Bonus.MA2;
        bonuses[1][5] = Bonus.ML3;
        bonuses[1][9] = Bonus.ML3;
        bonuses[1][13] = Bonus.MA2;
        bonuses[2][2] = Bonus.MA2;
        bonuses[2][6] = Bonus.ML2;
        bonuses[2][8] = Bonus.ML2;
        bonuses[2][12] = Bonus.MA2;
        bonuses[3][0] = Bonus.ML2;
        bonuses[3][3] = Bonus.MA2;
        bonuses[3][7] = Bonus.ML2;
        bonuses[3][11] = Bonus.MA2;
        bonuses[3][14] = Bonus.ML2;
        bonuses[4][4] = Bonus.MA2;
        bonuses[4][10] = Bonus.MA2;
        bonuses[5][1] = Bonus.ML3;
        bonuses[5][5] = Bonus.ML3;
        bonuses[5][9] = Bonus.ML3;
        bonuses[5][13] = Bonus.ML3;
        bonuses[6][2] = Bonus.ML2;
        bonuses[6][6] = Bonus.ML2;
        bonuses[6][8] = Bonus.ML2;
        bonuses[6][12] = Bonus.ML2;
        bonuses[7][0] = Bonus.MA3;
        bonuses[7][3] = Bonus.ML2;
        bonuses[7][7] = Bonus.Star;
        bonuses[7][11] = Bonus.ML2;
        bonuses[7][14] = Bonus.MA3;


        // The board is symmetrical
        for(int i = 8; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                bonuses[i][j] = bonuses[15 - i - 1][j];
            }
        }
    }
    private Word[] getWordsByFirst(Tile t){
        ArrayList<Word> wanted = new ArrayList<Word>();
        for(Word  w : words){
            if (w.getTiles()[0].equals(t)){
                wanted.add(w);
            }
        }
        return (Word[]) wanted.toArray();
    }
    /**
     * Get board.
     *
     * @return the board
     */
    public static  Board getBoard(){
        if(b == null)
            b = new Board();
        return b;
    }
    /**
     * Add Words.
     * @param {
     * add words to the board. throws an exception in case of illegal add. acts as a validator and a word added helper function.
     * }
     */
    public boolean boardLegal(Word w) throws IllegalArgumentException{ // need validation for placing words near other words.
        if (w.vertical) {
            return validateWord(w, w.getCol(), w.getRow(), board);
        } else {
            return validateWord(w,w.getRow(), w.getCol() ,transposeBoard(board));
        }
    }
    private boolean isEmpty(){
        for (int i = 0; i< 15; i++)
        {
            for (int j= 0; j <15;j++){
                if(board[i][j] != null){
                    return false;
                }
            }
        }
        return true;
    }
    private boolean validateWord (Word w, int Col, int Row, Tile[][] b) throws IllegalArgumentException{
        boolean flag = false;
        if ( w.getTiles().length + Row > 15 || Col < 0 || Col > 14 || Row < 0 || Row > 14){ // out of boundaries
            return false;
        }
        if(isEmpty() && Row+w.getTiles().length > 7 && Col == 7){ // board is empty and first word not on the star tile
            return true;
        }
        try{
            if(b[Row][Col-1] != null || b[Row-1][Col] != null || b[Row+1][Col] != null) // check the first tile surrounding tiles
                flag = true;
        }catch(Exception e){
      //      System.out.println("Tile placed on an edge" + " Word : " + w);
        }
        try{
            if(b[Row][Col+w.getTiles().length+1] != null || b[Row-1][Col+w.getTiles().length] != null || b[Row+1][Col+w.getTiles().length] != null) // check the last tile surroundings
                flag = true;
        }catch(Exception e){
       //     System.out.println("Tile placed on an edge" + " Word : " + w);
        }
        for (int i = 0; i < w.getTiles().length;i++){
            if (b[Row + i][Col] != null && board[Row + i][Col] != w.getTiles()[i] && w.getTiles()[i] != null){
                return false;
            }
            try {
                if (b[Row+i][(Col-1)] != null)
                    flag = true;
            }catch(Exception e){}
            try{
                if (b[Row+i][(Col+1)] != null)
                    flag = true;
            }catch (Exception e){}
        }
        return flag;
    }
    boolean dictionaryLegal(Word w){
        return true;
    }


    public ArrayList<Word> getWords(Word w) {
        Tile[][] copiedBoard = new Tile[15][15];
        for(int i = 0; i< 15; i++){
            for(int j = 0; j < 15;j++){
              copiedBoard[i][j] =  board[i][j];
            }
        }
        ArrayList<Word> allWords = new ArrayList<Word>();
        if (boardLegal(w)){
            if (w.isVertical()) {
                copiedBoard = place(copiedBoard, w, w.isVertical(), w.getRow(), w.getCol());


            } else {
                copiedBoard =  place(transposeBoard(copiedBoard),w,w.isVertical(), w.getCol(), w.getRow());
            }
                for (int col = 0; col < 15; col++) {
                    for (int row = 0; row < 15; row++) {
                        int originalRow = row;
                        ArrayList<Tile> word = new ArrayList<Tile>();
                        int currentRow = row;
                        if (copiedBoard[row][col] != null) {
                            while (copiedBoard[row][col] != null && row < 15) {
                                word.add(copiedBoard[row][col]);
                                currentRow++;
                                row = currentRow;
                            }
                            Word newWord = new Word(word.toArray(new Tile[0]),originalRow,col,true );
                            if (dictionaryLegal(newWord) && newWord.getTiles().length > 1){
                                allWords.add(newWord);
                            }
                        }
                    }
                } // loop all the vertical words

            for (int row = 0; row < 15; row++) {
                for (int col = 0; col < 15; col++) {
                    ArrayList<Tile> word = new ArrayList<Tile>();
                    int currentCol = col;
                    int originalCol = col;
                    if (copiedBoard[row][col] != null){
                        while (copiedBoard[row][col] != null && col < 15){
                            word.add(copiedBoard[row][col]);
                            currentCol++;
                            col = currentCol;
                        }
                        Word newWord = new Word(word.toArray(new Tile[0]),row,originalCol,false );
                        if (dictionaryLegal(newWord) && newWord.getTiles().length > 1){
                            allWords.add(newWord);
                        }
                    }
                }
            } // loop all the horizontal words

            ArrayList<Word> newWords = new ArrayList<>();
            Word[] allWordsArr = allWords.toArray(new Word[0]);
            //filter all the words with our words array

            boolean flag = true;
            for (int i = 0; i< allWordsArr.length;i++){
                for (int j = 0; j<words.length;j++){
                    if (allWordsArr[i].equals(words[j])){
                        flag = false;
                    }
                }
                if (flag){
                    newWords.add(allWordsArr[i]);
                }
                flag = true;
            }
            Word[] temp = allWordsArr.clone();
            words = temp;
            return newWords;
        }
        return null;
    }

    private Tile[][] place(Tile[][] b_clone, Word w, boolean isVertical, int Row, int Col){
        for (int i = 0; i < w.getTiles().length;i++){
            if (b_clone[Row+i][Col] == null)
            b_clone[Row+i][Col] = w.getTiles()[i];
            else{
                w.getTiles()[i] = board[w.getRow() + i][w.getCol()];
            }
        }
        if (!isVertical){
            b_clone = transposeBoard(b_clone);
        }
        return b_clone;
    }

    Tile[][] transposeBoard(Tile[][] b_clone) {

        int n = board.length, m = b_clone[0].length;

        // create empty transpose matrix of size m*n
        Tile[][] M_transpose = new Tile[m][n];

        // traverse matrix M
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                //assign M_transpose[j][i] as M[i][j]
                M_transpose[j][i] = b_clone[i][j];
            }
        }
        return M_transpose;
    }
    public int getScore(Word w){
        int score = 0;
        int wordMul = 1;
        if (w.isVertical()){
            for (int i = 0;i < w.getTiles().length;i++){

                if (w.getTiles()[i] == null){
                    i++;
                }
                if(bonuses[w.getCol()][w.getRow() + i] == Bonus.ML3){
                    score += w.getTiles()[i].getScore()*3;
                }
                if(bonuses[w.getCol()][w.getRow() + i] == Bonus.ML2){
                    score += w.getTiles()[i].getScore()*2;
                }
                if(bonuses[w.getCol()][w.getRow() + i] == Bonus.R){
                    score += w.getTiles()[i].getScore();
                }
                if(bonuses[w.getCol()][w.getRow() + i] == Bonus.MA2){
                    score += w.getTiles()[i].getScore();
                    wordMul *= 2;
                }
                if(bonuses[w.getCol()][w.getRow() + i] == Bonus.MA3){
                    score += w.getTiles()[i].getScore();
                    wordMul *= 3;
                }
                if (isEmpty() && bonuses[w.getCol()][w.getRow() + i] == Bonus.Star)
                {
                    score += w.getTiles()[i].getScore();
                    wordMul *= 2;
                    bonuses[w.getCol()][w.getRow() + i] = Bonus.R;
                }
            }
            score *= wordMul;
        }
         else{
            for (int i = 0;i < w.getTiles().length;i++){
                if (w.getTiles()[i] == null){
                    i++;
                }
                if(bonuses[w.getCol() + i][w.getRow()] == Bonus.ML3){
                    score += w.getTiles()[i].getScore()*3;
                }
                if(bonuses[w.getCol() + i][w.getRow()] == Bonus.ML2){
                    score += w.getTiles()[i].getScore()*2;
                }
                if(bonuses[w.getCol() + i][w.getRow()] == Bonus.R){
                    score += w.getTiles()[i].getScore();
                }
                if(bonuses[w.getCol() + i][w.getRow()] == Bonus.MA2){
                    score += w.getTiles()[i].getScore();
                    wordMul *= 2;
                }
                if(bonuses[w.getCol() + i][w.getRow()] == Bonus.MA3){
                    score += w.getTiles()[i].getScore();
                    wordMul *= 3;
                }
                if (isEmpty() && bonuses[w.getCol() + i][w.getRow()] == Bonus.Star)
                {
                    score += w.getTiles()[i].getScore();
                    wordMul *= 2;
                    bonuses[w.getCol() + i][w.getRow()] = Bonus.R;
                }
            }
            score *= wordMul;
        }
        return score;
    }
    public int tryPlaceWord(Word w){
       /* System.out.println("current words:");
        for (Word word : words){
            System.out.print(word.toString() + ",");
        }
        System.out.println("\nword wanted to add :" + w.toString());

        */
        int score = 0;
        if (!boardLegal(w))
            return -1;
        ArrayList<Word> newWords = getWords(w);
       // System.out.println("new words: ");
        for (Word word : newWords){
      //      System.out.println(word + ",");
            score += getScore(word);
        }

        if (w.vertical) {
            for (int i = 0; i < w.getTiles().length; i++) {
                if(board[w.getRow() + i][w.getCol()] == null)
                board[w.getRow() + i][w.getCol()] = w.getTiles()[i];
                else{
                    w.getTiles()[i] = board[w.getRow() + i][w.getCol()];
                }
            }
        }
        if (!w.vertical){
            for (int i = 0; i < w.getTiles().length; i++) {
                if(board[w.getRow()][w.getCol() + i] == null)
                    board[w.getRow()][w.getCol() + i] = w.getTiles()[i];
                else{
                    w.getTiles()[i] = board[w.getRow()][w.getCol() + i];
                }
            }
        }
        return score;
    }
    void printBoard(Tile[][] b){
        for (int i = 0; i < 15; i++){
            for (int j = 0;j < 15; j++){
                if (b[i][j] != null)
                System.out.print(b[i][j].letter + " ");
                else{
                    System.out.print( "_ ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("Words:");
        for (int i = 0; i < words.length; i++){
            if (words[i] != null){
                for (Tile t : words[i].tiles){
                    System.out.print(t.letter);
                }
                System.out.print(",");
            }
        }
        System.out.println("");
    }
}
