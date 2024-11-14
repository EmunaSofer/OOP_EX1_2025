public class BombDisc implements Disc
{
    private Player currentPlayer;
    public BombDisc(Player currentPlayer)
    {
        this.currentPlayer=currentPlayer;
    }

    @Override
    public Player getOwner() {
        return this.currentPlayer;
    }

    @Override
    public void setOwner(Player player)
    {
        this.currentPlayer=player;
    }

    @Override
    public String getType() {
        return "💣";
    }
}
