package objects;

import android.util.Log;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Player> Players = new ArrayList<Player>();
    private int goals = 0;
    private int points = 0;

public Team(String name, int teamSize)
{
    this.name = name;
    for (int i = 1; i<=teamSize; i++)
    {
        Players.add(new Player(i));
    }
}

public void changeName(String name)
    {
        this.name = name;
    }

public void updateScore(String type, int update)
    {
        switch (type)
        {
            case "goal":
            {
                goals = goals + update;
                update = 0;
            }
            case "point":
            {
                points = points + update;
                update = 0;
            }
        }
    }

    public int getGoals()
    {
        return goals;
    }

    public int getPoints()
    {
        return points;
    }

    public String getName()
    {
        return name;
    }


    public Player getPlayer(int jerseyNumber) {
        for (int i = 0; i < Players.size(); i++) {
            if (jerseyNumber == (Players.get(i).getJerseyNumber()));
            return Players.get(i);
        }
        return null;
    }

    public void clear()
    {
        goals = 0;
        points = 0;
        for (int i = 0; i<Players.size(); i++)
        {
            Players.set(i, new Player(i+1));
        }
    }
}