import java.util.ArrayList;
import java.util.List;

public class RandomAI extends AIPlayer {
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> choices = gameStatus.ValidMoves();
        Position P = choices.get(random(choices.size()));
        Disc[] type = new Disc[3];
        Move ans;
        if (isPlayerOne) {
            type[0] = new SimpleDisc(gameStatus.getFirstPlayer());

            type[1] = new BombDisc(gameStatus.getFirstPlayer());

            type[2] = new UnflippableDisc(gameStatus.getFirstPlayer());

            ans = new Move(P, type(type, gameStatus.getFirstPlayer()) );
        } else {
            type[0] = new SimpleDisc(gameStatus.getSecondPlayer());

            type[1] = new BombDisc(gameStatus.getSecondPlayer());

            type[2] = new UnflippableDisc(gameStatus.getSecondPlayer());

            ans = new Move(P, type(type, gameStatus.getSecondPlayer()));
        }

        return ans;
    }

    //פעולה שמגרילה מספר בין 0-K
    public static int random(int k) {
        int a;
        double r = Math.random();//הגרלת מספר בין 0-1
        r = r * k;
        a = (int) r;
        return a;
    }

        //פעולה שבוחרת את הסוג של הדיסק עם המגבלות
    public static Disc type(Disc[] discs, Player player) {
        if (player.getNumber_of_unflippedable() != 0 && player.getNumber_of_bombs() != 0) {
            int randomPlace = random(3);
            if (randomPlace == 1) {
                return discs[1];
            }
            if (randomPlace == 2) {
                return discs[2];
            }
            return discs[0];
        }
        if (player.getNumber_of_unflippedable() != 0) {
            int randomPlace = random(2);
            if (randomPlace == 1) {
                return discs[2];
            } else
                return discs[0];
        }
        if (player.getNumber_of_bombs() != 0) {
            int randomPlace = random(2);
            if (randomPlace == 1) {
                return discs[1];
            } else
                return discs[0];
        }

        return discs[0];
    }

}
