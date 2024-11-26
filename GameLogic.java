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
        if (isValidPosition(a)&&board[a.row()][a.col()]==null&& countFlips(a)>0 && o(disc))
        {
            if (disc instanceof BombDisc)
                disc.getOwner().reduce_bomb();
            if (disc instanceof  UnflippableDisc)
                disc.getOwner().reduce_unflippedable();
            board[a.row()][a.col()]=disc;
            if(firstPlayerTurn) {
                System.out.println("player 1 place a "+disc.getType()+" in ("+a.row+","+a.col+")");
            }
            else
            {
                System.out.println("player 2 place a "+disc.getType()+" in ("+a.row+","+a.col+")");
            }
            Move move = new Move(a,disc,C(a));
            stack.push(move);
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

        List<Position>A=new ArrayList<>();
        // עבור כל כיוון מתוך הכיוונים המוגדרים במערך
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // אם גם i וגם j הם 0, זה הכיוון המרכזי, לא נרצה לבדוק אותו
                if (i == 0 && j == 0) {
                    continue;
                }
                List<Position> help = countFlipsInDirection(a, currentPlayer, opponentPlayer, i, j);
                A.addAll(help);
            }
        }
        hasDuplicates(A);
        return A.size();
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
         while (!stack.isEmpty())
         {
             if (getDiscAtPosition(stack.pop().position).getOwner() == this.player1) {
                 discOfPlayer1++;
             } else {
                 discOfPlayer2++;
             }
         }
         if(discOfPlayer1>discOfPlayer2) {
             this.player1.wins++;
             System.out.println("player 1 win with "+discOfPlayer1+" disc! the player 2 had "+discOfPlayer2+" disc");
         }
         if (discOfPlayer2>discOfPlayer1)
         {
             this.player2.wins++;
             System.out.println("player 2 win with "+discOfPlayer2+" disc! the player 1 had "+discOfPlayer1+" disc");
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
            if (tempMove.disc instanceof BombDisc)
                tempMove.disc.getOwner().number_of_bombs++;
            if (tempMove.disc instanceof UnflippableDisc)
                tempMove.disc.getOwner().number_of_unflippedable++;
            board[tempMove.position.row][tempMove.position.col] = null;
            System.out.println("Undoing last move:");
            System.out.println("Undo: removing "+tempMove.disc.getType()+" from ("+tempMove.position.row+" , "+tempMove.position.col+")");

            for (int i = 0; i < tempMove.getList().size(); i++)
            {
                Disc temp = getDiscAtPosition(tempMove.getList().get(i));
                changColorBake(temp);
                System.out.println("Undo: flipping back "+tempMove.disc.getType()+ " in ("+tempMove.getList().get(i).row+" , "+tempMove.getList().get(i).col+")");
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

    public static boolean isValidPosition(Position pos) {
        return pos.col() >= 0 && pos.col() < 8 && pos.row() >= 0 && pos.row() < 8;
    }

    //הפעולה מחזירה לי רשימה של כל המקומות שיתהפכו
    public List<Position> countFlipsInDirection(Position start, Player currentPlayer, Player opponentPlayer, int dRow, int dCol) {
        int row = start.row() + dRow;
        int col = start.col() + dCol;
        List<Position>flips = new ArrayList<>();
        // נבדוק כל עוד המיקום חוקי והדיסק שייך ליריב
        while (isValidPosition(new Position(row, col)) && board[row][col] != null && board[row][col]. getOwner() == opponentPlayer)
        {
            Position P=new Position(row,col);
             if (!(getDiscAtPosition(P) instanceof UnflippableDisc))
             {
                     flips.add(P);
                 if (getDiscAtPosition(P)instanceof BombDisc)
                 {
                     List<Position>help=BomFlip(P,opponentPlayer);
                     for (int k = 0; k <help.size() ; k++)
                     {
                             flips.add(help.get(k));
                     }
                 }
             }
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

    //הפעולה משנה את הדיקסיות אחרי בחירת המיקום
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
            if (!(getDiscAtPosition(A.get(i))instanceof UnflippableDisc))
            {
                getDiscAtPosition(A.get(i)).setOwner(currentPlayer);
                if (currentPlayer == player1) {
                    System.out.println("player 1 flipped the " + getDiscAtPosition(A.get(i)).getType() + " in (" + A.get(i).row + " , " + A.get(i).col + " )");
                } else {
                    System.out.println("player 2 flipped the " + getDiscAtPosition(A.get(i)).getType() + " in (" + A.get(i).row + " , " + A.get(i).col + " )");
                }
                theMoves.add(A.get(i));
            }

        }

        return theMoves;

    }

    //פעולה שמחזירה אם בחרו דיסק מיוחד ואם אפשר להשתמש בו
    public boolean o(Disc disc) {
        boolean ans1=true;
        boolean ans2=true;
        if (disc instanceof BombDisc) {
            ans1 = (disc.getOwner().getNumber_of_bombs() != 0);// בדיקה שאין בעיה עם מספר ה "בוםדיסק"
        }
        if (disc instanceof UnflippableDisc) {
            ans2= (disc.getOwner().getNumber_of_unflippedable() != 0);//בדיקה שאין לנו בעיה עם הדיסק שלא מתהפך
        }
        return ans1 && ans2;
    }
    public List<Position > BomFlip(Position a ,Player opponentPlayer)
    {
        List<Position> ans=new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++)
            {
                // אם גם i וגם j הם 0, זה הכיוון המרכזי, לא נרצה לבדוק אותו
                if (i == 0 && j == 0) {
                    continue;
                }
                Position newPos = new Position(a.row() + i, a.col() + j);
                if (getDiscAtPosition(newPos)!=null)
                {
                    if (getDiscAtPosition(newPos).getOwner() == opponentPlayer)
                        ans.add(newPos);
                }
            }
        }
        return ans;
    }
    //פעולה שמקבלת רשימה ומחזירה את אותה רשימה רק בלי כפיליות
    public static void hasDuplicates(List<Position> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).col()==(list.get(j)).col() && list.get(i).row()==list.get(i).row()) {
                    list.remove(list.get(j));
                }
            }
        }
    }



}
