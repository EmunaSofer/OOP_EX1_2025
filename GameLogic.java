import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic{

    private Player player1;
    private Player player2;
    private Disc[][] board;
    private final int boardSize=8;
    private boolean firstPlayerTurn;
    protected Stack<Move> stack = new Stack<>();






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
        if(firstPlayerTurn) {
            System.out.println("player 1 place a ? in ("+a.row+","+a.col+")");
        }
        else
        {
            System.out.println("player 2 place a ? in ("+a.row+","+a.col+")");
        }
        Move move = new Move(a,disc,C(a));
        stack.push(move);
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
        if (ValidMoves().isEmpty())
        {
         int discOfPlayer1 =0;
         int discOfPlayer2=0;
         if (getDiscAtPosition(stack.pop().position).getOwner()==this.player1)
         {
             discOfPlayer1++;
         }
         else
         {
             discOfPlayer2++;
         }
         if(discOfPlayer1>discOfPlayer2)
             this.player1.wins++;
         System.out.println("player 1 win");
         if (discOfPlayer2>discOfPlayer1)
         {
             this.player2.wins++;
             System.out.println("player 2 win");
         }
        }
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
        this.stack.clear();
    }

    @Override
    public void undoLastMove() {
        if (!stack.isEmpty()){
            Move tempMove = this.stack.pop();
            board[tempMove.position.row][tempMove.position.col] = null;
            System.out.println("Undoing last move:");
            System.out.println("Undo: removing ? from ("+tempMove.position.row+" , "+tempMove.position.col+")");

            for (int i = 0; i < tempMove.getList().size(); i++)
            {
                Disc temp = getDiscAtPosition(tempMove.getList().get(i));
                changColorBake(temp);
                System.out.println("Undo: flipping back ? in ("+tempMove.getList().get(i).row+" , "+tempMove.getList().get(i).col+")");
            }
            this.firstPlayerTurn=!firstPlayerTurn;
        }
    }

    public void changColorBake(Disc disc) {
        Player temp = disc.getOwner();
        if (temp == getFirstPlayer()) {
            disc.setOwner(getSecondPlayer());
        }
        else
            disc.setOwner(getFirstPlayer());
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
    public  List<Position> C(Position a)
    {
        List<Position>A=new ArrayList<>();
        Player currentPlayer = firstPlayerTurn ? player1 : player2;
        Player opponentPlayer = firstPlayerTurn ? player2 : player1;
        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++) {
                // אם גם i וגם j הם 0, זה הכיוון המרכזי, לא נרצה לבדוק אותו
                if (i == 0 && j == 0) {
                    continue;
                }
                List<Position> help=new ArrayList<>(countFlipsInDirection(a, currentPlayer, opponentPlayer, i, j));
                A.addAll(help);
            }
        }

        List<Position> theMoves = new ArrayList<>();
        for (int i = 0; i <A.size() ; i++)
        {
            //if(getDiscAtPosition(A.get(i)).getType()!=BombDisc)
            getDiscAtPosition(A.get(i)).setOwner(currentPlayer);
            if (currentPlayer==player1) {
                System.out.println("player 1 flipped the ? in (" + A.get(i).row + " , " + A.get(i).col + " )");
            }
            else
            {
                System.out.println("player 2 flipped the ? in (" + A.get(i).row + " , " + A.get(i).col + " )");
            }
            theMoves.add(A.get(i));
        }
        return theMoves;
    }




}
