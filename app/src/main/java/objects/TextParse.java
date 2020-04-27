package objects;

import android.util.Log;

import java.util.ArrayList;



public class TextParse {
    private String[] numbers = {"one", "two|to|too", "three|tree", "four|for", "five|v", "six", "seven", "eight|it|ate", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    private String[] stats = {"goal|gold", "point|pint", "", "", "wide", "short", "turnover", "pass|past", "loss|lost", "award", "concede", "behind", "yellow", "red"};
    private int player;
    private Stat lastStat = new Stat();
    private int team;
    private String currentStat;
    private boolean newStat;
    private String teamOne;
    private String teamTwo;

    public TextParse(){}

    public void ParseText(String text, ArrayList<Team> Teams)
    {
        //Split the input into an array at each space
        String[] data = text.split(" ");
        //reset values
        int i = 0;
        player = 0;
        currentStat = "";
        newStat=false;
        teamOne = Teams.get(0).getName();
        teamTwo = Teams.get(1).getName();
        //if there is a value, parse it
        if (data.length > 1) {
            while (i < (data.length)) // while there is still words to parse
            {

                if (data[i].toLowerCase().matches("left|lift|list|let's|right|write|rice") || data[i].toLowerCase().matches(teamOne.toLowerCase()) || data[i].toLowerCase().matches(teamTwo.toLowerCase())) //if the next word is a team
                {

                    if (data[i].toLowerCase().matches("left|lift|list|let's") || data[i].toLowerCase().matches(teamOne.toLowerCase())) //Set the team value to 0 for home or 1 for away
                    {
                        team = 0;
                        lastStat.setTeam(teamOne);
                    } else if (data[i].toLowerCase().matches("right|write|rice") || data[i].toLowerCase().matches(teamTwo.toLowerCase())){
                        team = 1;
                        lastStat.setTeam(teamTwo);
                    }
                    i++;
                    player = getNumber(data[i]);
                    lastStat.setPlayer(player);
                    //check if it has returned ([number]pt)
                    if (data[i].length() >= 3  && data[i].substring(data[i].length()-2).toLowerCase().matches("pt")) {
                        if (data[i].length() == 3) {
                            player = getNumber(data[i].substring(0, 1));
                        }
                        else player = getNumber(data[i].substring(0,2));
                        lastStat.setPlayer(player);
                        data[i] = ("point");
                        i--;
                    }
                    //check if it has returned ([number].3)
                    else if  (data[i].length() >= 3 && data[i].substring(data[i].length()-2).matches(".3"))
                    {
                        if (data[i].length() == 3) {
                            player = getNumber(data[i].substring(0, 1));
                        }
                        else player = getNumber(data[i].substring(0,2));
                        lastStat.setPlayer(player);
                        data[i-1] = ("point");
                        data[i] = ("free");
                        i= i-2;
                    }
                    //check if there is a value after the number
                    if (i < data.length - 1) i++;
                    {
                        //If a number has been found
                        if (player != 0) {
                            for (int j = 0; j <= (stats.length - 1); j++) //compare next word to each stat
                            {
                                if (data[i].toLowerCase().matches(stats[j])) //if a match is found
                                {
                                    if (j < 2) //if the stat is a score, check if it is from play or from a free
                                    {
                                        Teams.get(team).updateScore(stats[j], 1);//It is a score, update the team score
                                        if (i != (data.length - 1)) {
                                            i++;
                                            if (data[i].toLowerCase().matches("free|placed|playlist")) //if it is from a free, increment the stat variable by 2
                                            {
                                                j = j + 2;
                                            } else i--; //if not, it is from play, so revert
                                        }
                                    }
                                    currentStat = getStat(j);
                                    if (!(currentStat.matches(""))) { //if you have found a stat
                                        lastStat.setStat(currentStat); //update value for undo function
                                        newStat=true; //allow a stat to be added
                                        Teams.get(team).getPlayer(player).logStat(currentStat, 1); //add the stat to the player
                                    }
                                    else newStat=false; //no stat found
                                }
                            }
                        }
                    }

                } else i++; //word doesn't match a team
            }
        }
    }

    //a match has been found with the array of stats
    private String getStat(int j) {
        switch (j)
        {
            case 0: //goal from play
            {
                lastStat.setOutput("scored a goal from play");
                return ("goalFP");
            }
            case 1: //point from play
            {
                lastStat.setOutput("scored a point from play");
                return ("pointFP");
            }
            case 2: //goal from a free
            {
                lastStat.setOutput("scored a goal from a placed ball");
                return ("goalFF");
            }
            case 3: //point from a free
            {
                lastStat.setOutput("scored a point from a placed ball");
                return ("pointFF");
            }
            case 4: //wide
            {
                lastStat.setOutput("hit the ball wide");
                return ("wide");
            }
            case 5: //short to goalkeeper
            {
                lastStat.setOutput("hit the ball short to the goalkeeper");
                return ("short");
            }
            case 6: //possession won
            {
                lastStat.setOutput("won possession");
                return ("posW");
            }
            case 7: //pass completed
            {
                lastStat.setOutput("passed the ball");
                return ("pass");
            }
            case 8: //possession lost
            {
                lastStat.setOutput("lost possession");
                return ("posL");
            }
            case 9: //won a free
            {
                lastStat.setOutput("won a free");
                return ("freeAward");
            }
            case 10: //gave away a free
            {
                lastStat.setOutput("gave away a free");
                return ("freeCon");
            }
            case 11: //won a 65
            {
                lastStat.setOutput("Won a 65");
                return ("65");
            }
            case 12: //yellow card
            {
                lastStat.setOutput("received a yellow card");
                return ("yellow");
            }
            case 13: //red card
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

    //get number value
    private int getNumber(String temp)
    {
        if (temp.matches("\\d|\\d+\\d")) //if temp matches a digit (8)
            return Integer.parseInt(temp);
        else for (int k = 0; k < numbers.length; k++)
        {
            if (temp.toLowerCase().matches(numbers[k])) //if temp matches the typed number value (eight)
                return k+1;
        }

        return 0;
    }

    public void undoStat(ArrayList<Team> Teams)
    {
        if (lastStat.getStat()!=null)
        {
            Teams.get(team).getPlayer(player).logStat(lastStat.getStat(), -1); //undo last statistic in the player object
            if (lastStat.getStat().matches("goalFP|pointFP|goalFF|pointFF")) {
                if (lastStat.getStat().matches("goalFP|goalFF")) {
                    Teams.get(team).updateScore("goal", -1); //remove a goal if that is the stat to be undone
                } else if (lastStat.getStat().matches("pointFP|pointFF")){
                    Teams.get(team).updateScore("point", -1); //remove a point if that is the stat to be undone
                }
            }
        }
    }

    public boolean isNewStat()
    {
        return newStat;
    } //Check if a stat has just been logged
}
