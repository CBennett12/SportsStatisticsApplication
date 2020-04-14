package objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Team implements Serializable{
    private String name;
    private ArrayList<Player> Players = new ArrayList<>();
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

void updateScore(String type, int update) {
    String[] temp = type.split("o");
    type = temp[0];
    Log.d("PARSING", type);
    if (type.matches("g"))
    {
        goals = goals + update;
        } else if (type.matches("p")){
        points = points + update;
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
            if (jerseyNumber == (Players.get(i).getJerseyNumber()))
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

    public String toString() {
        String[] stats = {"goal", "point", "wide", "short", "poss won", "poss lost", "free award", "free concede"};
        String output = "";
        int temp;
        for (String stat : stats)
        {
            temp = getMax(stat);
            if (temp != 0) output = output.concat(temp + "\n");
            else output = output.concat("\n");
        }
        return output;
    }

    private int getMax(String stat)
    {
        int max = 0;
        for (int i = 1; i < this.size(); i++)
        {
            if (Players.get(max).getStat(stat) < Players.get(i).getStat(stat))
                max = i;
        }
        if (max > 0)
        return Players.get(max).getJerseyNumber();
        else return 0;
    }

    public int size()
    {
        return Players.size();
    }

}