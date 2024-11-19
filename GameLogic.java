import java.util.ArrayList;
import java.util.List;

public class GameLogic implements PlayableLogic{

    private Player player1;
    private Player player2;
    private Disc[][] board;
    private final int boardSize=8;
    private boolean firstPlayerTurn;

    public GameLogic()
    {
        this.board=new Disc[boardSize][boardSize];
        this.firstPlayerTurn=true;
    }
    //להוסיף מצב של אותו דבר ליד
    @Override
    public boolean locate_disc(Position a, Disc disc)
    {
        if (isValidPosition(a)&&board[a.row()][a.col]==null&& countFlips(a)>0) {
        board[a.row()][a.col]=disc;
        return true;
        }
        return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position)
    {
        return isValidPosition(position)? board[position.row()][position.col()]:null;
    }

    @Override
    public int getBoardSize() {
        return this.boardSize;
    }

    @Override
    public List<Position> ValidMoves() {
        List<Position> validMoves = new ArrayList<>();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Position pos = new Position(row, col);
                if (board[row][col] == null) {
                    validMoves.add(pos);
                }
            }
        }
        return validMoves;
    }

    @Override
    public int countFlips(Position a)
    {
       return 1;
    }

    @Override
    public Player getFirstPlayer() {
        return this.player1;
    }

    @Override
    public Player getSecondPlayer() {
        return this.player2;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        this.player1=player1;
        this.player2=player2;
    }

    @Override
    public boolean isFirstPlayerTurn() {
        return this.firstPlayerTurn;
    }

    @Override
    public boolean isGameFinished() {

        return ValidMoves().isEmpty();
    }
    //צריך להוסיף את השחקנים ההתחלתים
    @Override
    public void reset() {
        this.board = new Disc[boardSize][boardSize];
        this.firstPlayerTurn = true;
        board[3][3] = new SimpleDisc(player1);
        board[4][4] = new SimpleDisc(player1);
        board[3][4] = new SimpleDisc(player2);
        board[4][3] = new SimpleDisc(player2);
    }

    @Override
    public void undoLastMove() {
        System.out.println("Undo not implement yet.");
    }
    public static boolean isValidPosition(Position pos)
    {
        return pos.col()>=0 && pos.col()<8 && pos.row()>=0 && pos.row()<8;

    }
}
