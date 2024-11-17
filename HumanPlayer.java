public class HumanPlayer extends Player
{
    //I want to choose if the push is work :)

    public HumanPlayer(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    boolean isHuman() {
        return true;
    }
}
