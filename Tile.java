package test;
import java.util.Random;
import java.util.Objects;

/**
 * The type Tile.
 */
public class Tile {
    /**
     * The Letter.
     */
    public final char letter;
    /**
     * The Score.
     */
    public final int score;
    private Tile(char l, int c){
        letter = l;
        score = c;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "letter=" + letter +
                ", score=" + score +
                '}';
    }

    /**
     * Gets letter.
     *
     * @return the letter
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * The type Bag.
     */
    public static class Bag{
        private static Bag b = null;
        private Bag(){
            tilesAmount[0] = 9;
            tilesAmount[1] = 2;
            tilesAmount[2] = 2;
            tilesAmount[3] = 4;
            tilesAmount[4] = 12;
            tilesAmount[5] = 2;
            tilesAmount[6] = 3;
            tilesAmount[7] = 2;
            tilesAmount[8] = 9;
            tilesAmount[9] = 1;
            tilesAmount[10] = 1;
            tilesAmount[11] = 4;
            tilesAmount[12] = 2;
            tilesAmount[13] = 6;
            tilesAmount[14] = 8;
            tilesAmount[15] = 2;
            tilesAmount[16] = 1;
            tilesAmount[17] = 6;
            tilesAmount[18] = 4;
            tilesAmount[19] = 6;
            tilesAmount[20] = 4;
            tilesAmount[21] = 2;
            tilesAmount[22] = 2;
            tilesAmount[23] = 1;
            tilesAmount[24] = 2;
            tilesAmount[25] = 1;
            tiles[0] = new Tile('A', 1);
            tiles[1] = new Tile('B', 3);
            tiles[2] = new Tile('C', 3);
            tiles[3] = new Tile('D', 2);
            tiles[4] = new Tile('E', 1);
            tiles[5] = new Tile('F', 4);
            tiles[6] = new Tile('G', 2);
            tiles[7] = new Tile('H', 4);
            tiles[8] = new Tile('I', 1);
            tiles[9] = new Tile('J', 8);
            tiles[10] = new Tile('K', 5);
            tiles[11] = new Tile('L', 1);
            tiles[12] = new Tile('M', 3);
            tiles[13] = new Tile('N', 1);
            tiles[14] = new Tile('O', 1);
            tiles[15] = new Tile('P', 3);
            tiles[16] = new Tile('Q', 10);
            tiles[17] = new Tile('R', 1);
            tiles[18] = new Tile('S', 1);
            tiles[19] = new Tile('T', 1);
            tiles[20] = new Tile('U', 1);
            tiles[21] = new Tile('V', 4);
            tiles[22] = new Tile('W', 4);
            tiles[23] = new Tile('X', 8);
            tiles[24] = new Tile('Y', 4);
            tiles[25] = new Tile('Z', 10);

        }

        /**
         * Get bag bag.
         *
         * @return the bag
         */
        public static Bag getBag(){
            if(b == null) {
                b = new Bag();
            }
            return b;
        }

        /**
         * The Tiles amount.
         */
        int[] tilesAmount = new int[26];
        /**
         * The Tiles.
         */
        Tile[] tiles = new Tile[26];

        /**
         * Get rand tile.
         *
         * @return the tile
         */
        public Tile getRand(){
            Random rand = new Random();
            int n = rand.nextInt(26);
            // now let's check if the bag is empty
            if(isEmpty())
                return null;
            // not empty! let's pick a non zero tile
            while (!nonZeroTile(n)){
                n = rand.nextInt(26);
            }
            // A = 65 and so on at the ascii table
            this.tilesAmount[tiles[n].getLetter() - 'A']--;
            return this.tiles[n];
        }

        /**
         * Get tile tile.
         *
         * @param t the t
         * @return the tile
         */
        public Tile getTile(char t){
            if(t < 'A' || t>'Z')
                return null;
            if (isEmpty())
                return null;
            if(!nonZeroTile(t - 'A'))
                return null;
            this.tilesAmount[t - 'A']--;
            return this.tiles[t - 'A'];
        }

        /**
         * Size int.
         *
         * @return the int
         */
        public int size(){
            int size = 0;
            for (int i = 0; i < 26; i++){
                size += tilesAmount[i];
            }
            return size;
        }

        /**
         * Put.
         *
         * @param t the t
         */
        public void put(Tile t){
            if(this.size() == 98){
                return;
            }
            tilesAmount[t.getLetter() - 'A']++;
        }

        /**
         * Get quantities int [ ].
         *
         * @return the int [ ]
         */
        public int[] getQuantities(){
            int[] clone = new int[26];
            for (int i = 0; i < 26;i++)
            {
                clone[i] = tilesAmount[i];
            }
            return clone;
        }

        /**
         * Is empty boolean.
         *
         * @return the boolean
         */
//*********************************//
        //check if our bag is empty
        public boolean isEmpty(){
            for (int i = 0; i < this.tilesAmount.length; i++){
                if(tilesAmount[i] != 0)
                {
                    return false;
                }
            }
            return true;
        }

        /**
         * Non zero tile boolean.
         *
         * @param tile the tile
         * @return the boolean
         */
// check if a given tile has zero pieces of it.
        public boolean nonZeroTile(int tile){
            // might need to change this
            if (tilesAmount[tile] == 0)
                return false;
            return true;
        }
        //*********************************//
    }
}
