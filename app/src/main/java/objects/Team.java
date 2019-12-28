package objects;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Player> Players;
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

public void updateScore(String type, int update)
    {
        switch (type){
            case "goal": goals = goals + update;
            case "point": points = points + update;
        }
    }

}