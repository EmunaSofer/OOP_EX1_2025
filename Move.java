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

    public static int movePosition(Position position, Player currentPlayer, Player otherPlayer)
    {
         Position left = new Position(position.row, position.col - 1);
         Position right = new Position(position.row, position.col + 1);
         Position up = new Position(position.row - 1, position.col);
         Position down = new Position(position.row + 1, position.col);
         Position leftUp = new Position(position.row - 1, position.col - 1);
         Position leftDoun = new Position(position.row + 1, position.col - 1);
         Position rightUp = new Position(position.row + 1, position.col - 1);
         Position rightDoun = new Position(position.row + 1, position.col + 1);

         if(GameLogic.isValidPosition(left) && left.getDisc().getOwner()==otherPlayer){


         }
        if(GameLogic.isValidPosition(right) && right.getDisc().getOwner()==otherPlayer){

        }
        if(GameLogic.isValidPosition(up) && up.getDisc().getOwner()==otherPlayer){

        }
        if(GameLogic.isValidPosition(down) && down.getDisc().getOwner()==otherPlayer){

        }
        if(GameLogic.isValidPosition(leftUp) && leftUp.getDisc().getOwner()==otherPlayer){

        }
        if(GameLogic.isValidPosition(leftDoun) && leftDoun.getDisc().getOwner()==otherPlayer){

        }
        if(GameLogic.isValidPosition(rightUp) && rightUp.getDisc().getOwner()==otherPlayer){

        }
        if(GameLogic.isValidPosition(rightDoun) && rightDoun.getDisc().getOwner()==otherPlayer){

        }
        return 0;
    }
}
