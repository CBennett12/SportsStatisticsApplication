package objects;

import java.util.ArrayList;

public class Game {
    private ArrayList<Team> Teams = new ArrayList<>();

    public Game(String[] teamNames, int teamSize)
    {
        for (int i = 0; i<2; i++)
        {
            Teams.add(new Team(teamNames[i], teamSize));
        }
    }

    public Team returnTeam(int index)
    {
        return Teams.get(index);
    }

}