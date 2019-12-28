package objects;

import java.util.ArrayList;

public class Game {
    private ArrayList<Team> Teams = new ArrayList<>(2);

    public Game(String[] teamNames, int teamSize)
    {
        for (int i = 0; i<2; i++)
        {
            Teams.set(i, new Team(teamNames[i], teamSize));
        }
    }
}
