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
        if (player1.isPlayerOne) {
            return movePosition(a, player1, player2).size();
        }
        else
            return movePosition(a,player2,player1).size();
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

    public static List<Position> movePosition(Position position, Player currentPlayer, Player otherPlayer)
    {
        List<Position> ans=new ArrayList<>();
        List<Position> help= new ArrayList<>();
        Position left = new Position(position.row, position.col - 1);
        Position right = new Position(position.row, position.col + 1);
        Position up = new Position(position.row - 1, position.col);
        Position down = new Position(position.row + 1, position.col);
        Position leftUp = new Position(position.row - 1, position.col - 1);
        Position leftDoun = new Position(position.row + 1, position.col - 1);
        Position rightUp = new Position(position.row + 1, position.col - 1);
        Position rightDoun = new Position(position.row + 1, position.col + 1);

        while (cheak(left,otherPlayer))
        {
            help.add(left);
            left.setCol(left.col-1);
        }
        if (left.getDisc().getOwner() == currentPlayer)
        {
            for (int i = 0; i < help.size(); i++)
            {
                ans.add(help.get(i));
            }
        }
        help.clear();

        while (cheak(right,otherPlayer))
        {
            help.add(right);
            right.setCol(right.col+1);
        }
        if (right.getDisc().getOwner() == currentPlayer)
        {
            for (int i = 0; i < help.size(); i++)
            {
                ans.add(help.get(i));
            }
        }
        help.clear();

        while (cheak(up,otherPlayer))
        {
            help.add(up);
            up.setRow(up.row-1);
        }
        if (up.getDisc().getOwner() == currentPlayer)
        {
            for (int i = 0; i < help.size(); i++)
            {
                ans.add(help.get(i));
            }
        }
        help.clear();

        while (cheak(down,otherPlayer))
        {
            help.add(down);
            down.setRow(down.row+1);

        }
        if (down.getDisc().getOwner() == currentPlayer)
        {
            for (int i = 0; i < help.size(); i++)
            {
                ans.add(help.get(i));
            }
        }
        help.clear();

        while (cheak(leftUp,otherPlayer))
        {
            help.add(leftUp);
            leftUp.setCol(leftUp.col-1);
            leftUp.setRow(leftUp.row-1);
        }
        if (leftUp.getDisc().getOwner() == currentPlayer)
        {
            for (int i = 0; i < help.size(); i++)
            {
                ans.add(help.get(i));
            }
        }
        help.clear();

        while (cheak(leftDoun,otherPlayer))
        {
            help.add(leftDoun);
            leftDoun.setCol(leftDoun.col-1);
            leftDoun.setRow(leftDoun.row+1);
        }
        if (leftDoun.getDisc().getOwner() == currentPlayer)
        {
            for (int i = 0; i < help.size(); i++)
            {
                ans.add(help.get(i));
            }
        }
        help.clear();

        while (cheak(rightUp,otherPlayer))
        {
            help.add(rightUp);
            rightUp.setCol(rightUp.col+1);
            rightUp.setRow(rightUp.row-1);
        }
        if (rightUp.getDisc().getOwner() == currentPlayer)
        {
            for (int i = 0; i < help.size(); i++)
            {
                ans.add(help.get(i));
            }
        }
        help.clear();

        while (cheak(rightDoun,otherPlayer))
        {
            help.add(rightDoun);
            rightDoun.setCol(rightDoun.col+1);
            rightDoun.setRow(rightDoun.row+1);
        }
        if (rightDoun.getDisc().getOwner() == currentPlayer)
        {
            for (int i = 0; i < help.size(); i++)
            {
                ans.add(help.get(i));
            }
        }
        help.clear();

        return  ans;
    }
    public static boolean cheak(Position position,Player otherPlayer)
    {
        return GameLogic.isValidPosition(position) && position.getDisc().getOwner() == otherPlayer&&position.getDisc()==null;
    }
}
