import java.util.ArrayList;

public class Position {
    protected int row;
    protected int col;
    protected Disc disc;

    public Position(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int col() {
        return col;
    }

    public int row() {
        return row;
    }
    public Disc getDisc(){
        return this.disc;
    }

}
