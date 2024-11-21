import java.util.List;

public class GreedyAI extends AIPlayer {

    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position>choices = gameStatus.ValidMoves();
        List<Position>P = gameStatus.ValidMoves();
        Position p=choices.get(0);
        P.add(choices.get(0));
        for (int i = 1; i < choices.size(); i++) {
            if (gameStatus.countFlips(p)<gameStatus.countFlips(choices.get(i)))
            {
                p=choices.get(i);
                P.clear();
                P.add(choices.get(i));
            }
            if (gameStatus.countFlips(p)==gameStatus.countFlips(choices.get(i)))
            {
                P.add(choices.get(i));
            }
        }
        Position Pos= P.get(random(P.size()));
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
            Move ans=new Move(Pos, type[random(3)], choices );
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
