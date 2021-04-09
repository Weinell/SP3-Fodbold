public class Player {


    protected String name;
    protected int ID;
    static int counter = 0;


    public Player(String name)   {

        this.name = name;
        this.ID = counter;
        counter++;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }



}
