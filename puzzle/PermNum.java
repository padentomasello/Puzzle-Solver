package puzzle;
/** class that creates all necessary permuations of Entities.
 * @author Daniel Paden Tomasello cs61b-bz. */
public class PermNum {
    /** array representing Baselike number. */
    private int[] number;
    /** Base. */
    private int x;
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }
    /**
    * set x to IND.
    */
    public void setX(int ind) {
        this.x = ind;
    }
    /**
     * @return the number
     */
    public int[] getNumber() {
        return number;
    }
    /**
     * Sets number to NUMBER1.
     */
    public void setNumber(int[] number1) {
        this.number = number1;
    }
    /** Contructor which sets length of number to LEN and X1 is base. */
    PermNum(int x1, int len) {
        x = x1;
        number = new int[len];
        for (int i = 0; i < len; i += 1) {
            number[i] = 0;
        }
    }
    /** increments number at index D. */
    public void increment(int d) {
        number[d] = number[d] + 1;
        if (number[d] == x) {
            number[d] = 0;
            if (d < number.length - 1) {
                increment(d + 1);
            }
        }
    }
    /** Returns number at index X1. */
    public int get(int x1) {
        return number[x1];
    }
    /** returns number of total increments until 0 .*/
    public double numIncr() {
        return Math.pow(x, number.length);
    }
}  
