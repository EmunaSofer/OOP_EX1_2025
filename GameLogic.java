import java.util.ArrayList;
import java.util.List;

public class GameLogic implements PlayableLogic{

    private Player player1;
    private Player player2;
    private Disc[][] board;
    private final int boardSize=8;
    private boolean firstPlayerTurn;





    public GameLogic() {
        this.board = new Disc[boardSize][boardSize];
        this.firstPlayerTurn = true;
    }

    //להוסיף מצב של אותו דבר ליד
    @Override
    public boolean locate_disc(Position a, Disc disc)
    {
        if (isValidPosition(a)&&board[a.row()][a.col]==null&& countFlips(a)>0) {
        board[a.row()][a.col]=disc;
        C(a);
        this.firstPlayerTurn=!firstPlayerTurn;

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
        List<Position>ans=new ArrayList<>();

            for (int i = 0; i <8 ; i++) {
                for (int j = 0; j < 8; j++) {
                    Position position = new Position(i, j);
                    if (countFlips(position) > 0) {
                        ans.add(position);
                    }
                }

            }
            return ans;

    }

    @Override
    public int countFlips(Position a)
    {

        if (!isValidPosition(a) || board[a.row()][a.col()] != null) {
            return 0; // הנקודה תפוסה או לא חוקית
        }

        Player currentPlayer = firstPlayerTurn ? player1 : player2;
        Player opponentPlayer = firstPlayerTurn ? player2 : player1;

        int totalFlips = 0;

        // עבור כל כיוון מתוך הכיוונים המוגדרים במערך
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // אם גם i וגם j הם 0, זה הכיוון המרכזי, לא נרצה לבדוק אותו
                if (i == 0 && j == 0) {
                    continue;
                }
                totalFlips += countFlipsInDirection(a, currentPlayer, opponentPlayer, i, j).size();
            }
        }

        return totalFlips;
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

    private List<Position> countFlipsInDirection(Position start, Player currentPlayer, Player opponentPlayer, int dRow, int dCol) {
        int row = start.row() + dRow;
        int col = start.col() + dCol;
        List<Position>flips = new ArrayList<>();
        // נבדוק כל עוד המיקום חוקי והדיסק שייך ליריב
        while (isValidPosition(new Position(row, col)) && board[row][col] != null && board[row][col].getOwner() == opponentPlayer) {
            flips.add(new Position(row, col));
            row += dRow;
            col += dCol;
        }

        // אם הגענו לדיסק של השחקן הנוכחי, ההיפוכים תקפים
        if (isValidPosition(new Position(row, col)) && board[row][col] != null && board[row][col].getOwner() == currentPlayer) {
            return flips;
        }

        flips.clear();
        // אחרת, אין רצף חוקי בכיוון הזה
        return flips;
    }

    //הפעולה משנה את הדיקסיות אחרי בחירת המיקו
    public void C(Position a)
    {
        List<Position>A=new ArrayList<>();
        Player currentPlayer = firstPlayerTurn ? player1 : player2;
        Player opponentPlayer = firstPlayerTurn ? player2 : player1;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // אם גם i וגם j הם 0, זה הכיוון המרכזי, לא נרצה לבדוק אותו
                if (i == 0 && j == 0) {
                    continue;
                }
                List<Position> help=new ArrayList<>(countFlipsInDirection(a, currentPlayer, opponentPlayer, i, j));
                A.addAll(help);
            }

        }
        for (int i = 0; i <A.size() ; i++) {
            getDiscAtPosition(A.get(i)).setOwner(currentPlayer);
        }
    }




}
