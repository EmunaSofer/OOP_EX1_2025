import java.util.ArrayList;
import java.util.List;

public class GreedyAI extends AIPlayer {

    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position>choices = gameStatus.ValidMoves();
        List<Position> P = new ArrayList<>();
        Position p=choices.get(0);
        P.add(choices.get(0));
        for (int i = 1; i < choices.size(); i++) // בונה רשימה עם המקומות עם הכי הרבה החלפות
        {
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
        //בדיקה של האיבר הכי ימני
        if (P.size()>1)
        {
            for (int i = 0; i <P.size()-1 ; i++) {
                if (P.get(i).col()>P.get(i+1).col())
                    P.remove(P.get(i+1));
                else if (P.get(i).col()<P.get(i+1).col()) {
                    P.remove(P.get(i));
                }
            }
        }
        //בדיקת האיבר הכי למטה
        if (P.size()>1)
        {
            for (int i = 0; i <P.size()-1 ; i++) {
                if (P.get(i).row()>P.get(i+1).row())
                    P.remove(P.get(i+1));
                else if (P.get(i).row()<P.get(i+1).row())
                    P.remove(P.get(i));

            }
        }

        SimpleDisc type=new SimpleDisc(gameStatus.getSecondPlayer());
        Move ans;
        ans = new Move(P.get(0), type );
        return ans;

    }
}
