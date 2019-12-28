package objects;

public class Player {
    private String name;
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
        this.name = name;
        this.number = number;
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
                this.redCard = this.yellowCard == 2;

            }

            case "red":
            {
                 {
                    this.redCard = change == 1;
                }
            }

        }

    }



}
