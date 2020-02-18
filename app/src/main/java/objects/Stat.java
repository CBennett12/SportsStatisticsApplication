package objects;

public class Stat{
    int player;
    String Team;
    String stat;

    public void setPlayer(int player)
    {
        this.player = player;
    }

    public void setTeam(String Team)
    {
        this.Team = Team;
    }

    public void setStat(String stat)
    {
        this.stat = stat;
    }


    public String toString()
    {
        if (player != 0 && Team != null && stat != null)
        {
            return (Team + " number " + player + " " + stat);
        }
        else return null;
    }
}
