import java.util.ArrayList;
import java.util.List;

public class Move
{
    protected Position position;
    protected Disc disc;

    public Position position()
    {

        return this.position;
    }
    public Disc disc()
    {

        return this.disc;
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


