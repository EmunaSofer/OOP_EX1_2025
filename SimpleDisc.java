public class SimpleDisc implements Disc
{
    private Player currentPlayer;

//פעולות בנאי
    public SimpleDisc(Player currentPlayer)
    {
        this.currentPlayer=currentPlayer;
    }
//מחזיר את המשתמש
    @Override
    public Player getOwner()
    {
        return this.currentPlayer;
    }
//מעדכן את הבעלים
    @Override
    public void setOwner(Player player)
    {
        this.currentPlayer=player;
    }
// מחזיר את ה-type
    @Override
    public String getType() {
        return "⬤";
    }
}
