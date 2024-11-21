import java.util.ArrayList;

public class Position {
    protected int row;
    protected int col;
    protected Player player;


    public Position(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public Position(int row, int col, Player player) {
        this.col = col;
        this.row = row;
        this.player = player;
    }



    public void setRow(int row)
    {
        this.row=row;
    }
    public void setCol(int col)
    {
        this.col=col;
    }
    public int col() {
        return col;
    }

    public int row() {
        return row;
    }


}
