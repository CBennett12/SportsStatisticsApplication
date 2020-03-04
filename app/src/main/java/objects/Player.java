package objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Player implements Serializable {
    private int number;
    private int goalFP = 0;
    private int pointFP = 0;
    private int goalFF = 0;
    private int pointFF = 0;
    private int wide = 0;
    private int shortTG = 0;
    private int posW = 0;
    private int passRec = 0;
    private int posL = 0;
    private int freeAward = 0;
    private int freeCon = 0;
    private int sixtyFive = 0;
    private int yellowCard = 0;
    private boolean redCard = false;


    public Player(String name, int number)
    {
        this.number = number;
    }

    public Player ()
    {

    }

    public Player(int number)
    {
        this.number = number;
    }

    public void logStat(String stat, int change)
    {

        switch (stat)
        {
            case "pointFP":
            {
                this.pointFP = pointFP + change;
            }

            case "goalFP":
            {
                this.goalFP = goalFP + change;
            }
            case "pointFF":
            {
                this.pointFF = pointFF + change;
            }

            case "goalFF":
            {
                this.goalFF = goalFF + change;
            }

            case "wide":
            {
                this.wide = wide + change;
            }
            case "short":
            {
                this.shortTG = shortTG + change;
            }

            case "posW":
            {
                this.posW =posW + change;
            }

            case "pass":
            {
                this.passRec = passRec + change;
            }

            case "posL":
            {
                this.posL= posL + change;
            }

            case "freeAward":
            {
                this.freeAward = freeAward + change;
            }

            case "freeCon":
            {
                this.freeCon = freeCon + change;
            }

            case "65":
            {
                this.sixtyFive = sixtyFive + change;
            }

            case "yellow":
            {
                this.yellowCard = yellowCard + change;
                redCard = this.yellowCard == 2;

            }

            case "red":
            {
                 {
                    redCard = change == 1;
                }
            }

        }

    }

    public int getJerseyNumber()
    {
        return number;
    }

    public int getStat(String stat)
    {
            /*
            int number;
            int goalFP;
            int pointFP;
            int goalFF;
            int pointFF;
            int wide;
            int shortTG;
            int posW;
            int passRec
            int posL;
            int freeAward;
            int freeCon;
            int sixtyFive
            int yellowCard
            bool redCard
             */

                 if (stat.matches("number"))
                    return this.number;
            else if (stat.matches("goal"))
                    return this.goalFP+goalFF;
            else if (stat.matches("goalff"))
                    return this.goalFF;
            else if (stat.matches("point"))
                    return this.pointFP+pointFF;
            else if (stat.matches("pointff"))
                    return this.pointFF;
            else if (stat.matches( "wide"))
                    return this.wide;
            else if (stat.matches("short"))
                    return this.shortTG;
            else if (stat.matches("poss won"))
                    return this.posW;
            else if (stat.matches("poss lost"))
                    return this.posL;
                 else if (stat.matches("pass"))
                     return this.passRec;
            else if (stat.matches("free award"))
                    return this.freeAward;
            else if (stat.matches("free concede"))
                    return this.freeCon;
            else if (stat.matches("65"))
                    return this.sixtyFive;
            else if (stat.matches("yellow"))
                    return this.yellowCard;
            else if (stat.matches("red"))
            {
                if (redCard)
                {
                    return 1;
                }
                else return 0;

            }
            else
                return 0;
    }

}
