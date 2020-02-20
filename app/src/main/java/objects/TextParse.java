package objects;

import android.util.Log;

import java.util.ArrayList;



public class TextParse {
    String[] numbers = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    private String[] stats = {"goal", "point", "", "", "miss", "short", "turnover", "pass", "loses", "award", "concede", "sixty", "yellow", "red"};
    private int player;
    private Stat lastStat = new Stat();
    private int team;
    private String currentStat;

    public TextParse(){}

    public void ParseText(String text, ArrayList<Team> Teams)
    {
        Log.d("PARSING", text);
        String[] data = text.split(" ");
        int i = 0;
        player = 0;

        while (i <= (data.length-1)) // while there is still words to parse
        {

            if (data[i].toLowerCase().matches("left|right|write")) //if the next word is a team
            {

                if (data[i].toLowerCase().matches("left")) //Set the team value to 0 for home or 1 for away
                {
                    team = 0;
                    lastStat.setTeam(Teams.get(0).getName());
                }
                else
                {
                    team = 1;
                    lastStat.setTeam(Teams.get(1).getName());
                }
                i++;
                player = getNumber(data[i]);
                lastStat.setPlayer(player);
                if (data[i].matches("(\\d+pt|PT)|(\\d+\\d+pt|PT)"))
                {
                    player = getNumber(data[i].substring(0,(data[i].length()-3)));
                    lastStat.setPlayer(player);
                    data[i]=("point");
                i--;
                }
                    if (i < data.length-1)i++;
                {
                    if (player != 0) ;
                    {
                        for (int j = 0; j <= (stats.length - 1); j++) //compare next word to each stat
                        {
                            if (data[i].toLowerCase().matches(stats[j])) //if a match is found
                            {
                                if (j < 2) //if the stat is a score, check if it is from play or from a free
                                {
                                    Teams.get(team).updateScore(stats[j], 1);
                                    if (i != (data.length - 1)) {
                                        i++;
                                        if (data[i].toLowerCase().matches("free")) //if it is from a free, increment the stat variable by 2
                                        {
                                            j = j + 2;
                                        } else i--; //if not, it is from play, so revert
                                    }
                                }
                                currentStat = getStat(j);
                                Log.d("PARSING", currentStat);
                                lastStat.setStat(currentStat);
                                Teams.get(team).getPlayer(player).logStat(currentStat, 1);
                            }
                        }
                    }
                }

            }
            else i++;
        }
    }

    private String getStat(int j) {
        switch (j)
        {
            case 0:
            {
                lastStat.setOutput("scored a goal from play");
                return ("goalFP");
            }
            case 1:
            {
                lastStat.setOutput("scored a point from play");
                return ("pointFP");
            }
            case 2:
            {
                lastStat.setOutput("scored a goal from a placed ball");
                return ("goalFF");
            }
            case 3:
            {
                lastStat.setOutput("scored a point from a placed ball");
                return ("pointFF");
            }
            case 4:
            {
                lastStat.setOutput("hit the ball wide");
                return ("wide");
            }
            case 5:
            {
                lastStat.setOutput("hit the ball short to the goalkeeper");
                return ("short");
            }
            case 6:
            {
                lastStat.setOutput("won possession");
                return ("posW");
            }
            case 7:
            {
                lastStat.setOutput("passed the ball");
                return ("pass");
            }
            case 8:
            {
                lastStat.setOutput("lost possession");
                return ("posL");
            }
            case 9:
            {
                lastStat.setOutput("won a free");
                return ("freeAward");
            }
            case 10:
            {
                lastStat.setOutput("lost a free");
                return ("freeCon");
            }
            case 11:
            {
                lastStat.setOutput("Won a 65");
                return ("65");
            }
            case 12:
            {
                lastStat.setOutput("received a yellow card");
                return ("yellow");
            }
            case 13:
            {
                lastStat.setOutput("received a red card");
                return ("red");
            }

        }
        return null;
    }

    public Stat getLastStat()
    {
        return lastStat;
    }

    int getNumber(String temp)
    {
        if (temp.matches("\\d|\\d+\\d"))
            return Integer.parseInt(temp);
        else for (int k = 0; k < numbers.length; k++)
        {
            if (temp.toLowerCase().matches(numbers[k]))
                return k+1;
        }

        return 0;
    }

    public void undoStat(ArrayList<Team> Teams)
    {
        Teams.get(team).getPlayer(player).logStat(lastStat.getStat(), -1);
        if (lastStat.getStat().matches("goalFP|pointFP|goalFF|pointFF"))
        {
            if (lastStat.getStat().matches("goalFP|goalFF"))
            {
                Teams.get(team).updateScore("goal", -1);
            }

            else
                {
                    Teams.get(team).updateScore("point", -1);
                }
        }

    }
}
