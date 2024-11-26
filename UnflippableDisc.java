public class UnflippableDisc implements Disc {
    private Player currentPlayer;
    public UnflippableDisc(Player currentPlayer)
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
    public String getType()
    {
        return "⭕" ;
    }
}
