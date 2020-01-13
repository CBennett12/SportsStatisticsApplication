package objects;

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
        System.out.println(name);
    }

public void updateScore(String type, int update)
    {
        switch (type){
            case "goal": goals = goals + update;
            case "point": points = points + update;
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

    public Player getPlayer(int jerseyNumber) {
        for (int i = 0; i < Players.size(); i++) {
            if (jerseyNumber == (Players.get(i).getJerseyNumber()));
            return Players.get(i);
        }
        return null;
    }
}