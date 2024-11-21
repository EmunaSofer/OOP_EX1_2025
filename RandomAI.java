import java.util.ArrayList;
import java.util.List;

public class RandomAI extends AIPlayer {
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> choices = gameStatus.ValidMoves();
        Position P= choices.get(random(choices.size()));
        Disc[] type =new Disc[3];
        type[0]=new SimpleDisc(gameStatus.getFirstPlayer());
        if (gameStatus.getFirstPlayer().number_of_bombs<3) {
            type[1] = new BombDisc(gameStatus.getFirstPlayer());
        }
        else
            type[1] = new SimpleDisc(gameStatus.getFirstPlayer());
        if (gameStatus.getFirstPlayer().number_of_unflippedable<2) {
            type[2] = new UnflippableDisc(gameStatus.getFirstPlayer());
        }
        else
            type[2] = new SimpleDisc(gameStatus.getFirstPlayer());
        Move ans=new Move(P, type[random(3)], choices );
        return ans;
    }
    public  static int random (int k)
    {
        int a;
        double r=Math.random();//הגרלת מספר בין 0-1
        r=r*k;
        a= (int) r;
        return a;
    }

}
