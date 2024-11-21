import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// מחלקה זו הולכת לשמור לי את כל המהלכים שאני מבצעת במשחק שלב אחרי שלב - מעין מחסנית של רשימות
public class Move
{
    protected Position position;
    protected Disc disc;
    private List<Position> countFlips;
    public Move(Position position, Disc disc, List<Position>a) {
        this.position = position;
        this.disc = disc;
        this.countFlips = a;
    }
    public Position position()
    {
        return this.position;
    }
    public Disc disc()
    {
        return this.disc;
    }

    public List<Position> getList(){
        return this.countFlips;
    }


    //פונקציה להוספת תור במשחק (הנותן רשימה חדשה למחסנית)
//    public void addPackageMoves(CustomStack stack, ArrayList<Position> newPackage)
//    {
//        this.stack.getStack().push(newPackage);
//    }

//    public ArrayList<Position> removeLastPackage(CustomStack stack){
//        ArrayList<Position> undo = this.stack.getStack().pop();
//        return undo;
//    }

// make - getList




}


