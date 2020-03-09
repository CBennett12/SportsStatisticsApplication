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

            if (stat.matches("pointFP"))
            {
                this.pointFP = pointFP + change;
                if (this.pointFP < 0)
                    this.pointFP = 0;
            }

            else if (stat.matches("goalFP"))
            {
                this.goalFP = goalFP + change;
                if (this.goalFP < 0)
                    this.goalFP = 0;
            }
            else if (stat.matches("pointFF"))
            {
                this.pointFF = pointFF + change;
                if (this.pointFF < 0)
                    this.pointFF = 0;
            }

            else if (stat.matches("goalFF"))
            {
                this.goalFF = goalFF + change;
                if (this.pointFF < 0)
                    this.goalFF = 0;
            }

            else if (stat.matches("wide"))
            {
                this.wide = wide + change;
                if (this.wide < 0)
                    this.wide = 0;
            }
            else if (stat.matches("short"))
            {
                this.shortTG = shortTG + change;
                if (this.shortTG < 0)
                    this.shortTG = 0;
            }

            else if (stat.matches("posW"))
            {
                this.posW =posW + change;
                if (this.posW < 0)
                    this.posW = 0;
            }

            else if (stat.matches("pass"))
            {
                this.passRec = passRec + change;
                if (this.passRec < 0)
                    this.passRec = 0;
            }

            else if (stat.matches("posL"))
            {
                this.posL= posL + change;
                if (this.posL < 0)
                    this.posL = 0;
            }

            else if (stat.matches("freeAward"))
            {
                this.freeAward = freeAward + change;
                if (this.freeAward < 0)
                    this.freeAward = 0;
            }

            else if (stat.matches("freeCon"))
            {
                this.freeCon = freeCon + change;
                if (this.freeCon < 0)
                    this.freeCon = 0;
            }

            else if (stat.matches("65"))
            {
                this.sixtyFive = sixtyFive + change;
                if (this.sixtyFive < 0)
                    this.sixtyFive = 0;
            }

            else if (stat.matches("yellow"))
            {
                this.yellowCard = yellowCard + change;
                redCard = this.yellowCard == 2;
                if (this.yellowCard < 0)
                    this.yellowCard = 0;

                if (this.yellowCard >2)
                    this.yellowCard = 2;

            }

            else if (stat.matches("red"))
            {
                 {
                    redCard = change == 1;
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
