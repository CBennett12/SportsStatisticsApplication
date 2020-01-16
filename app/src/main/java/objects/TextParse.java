package objects;

import java.util.ArrayList;

public class TextParse {
    String[] data;
    String[] numbers = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    String[] stats = {"point", "goal", "wide", "short", "possession", "passes", "loses", "wins", "away", "sixty", "yellow", "red"};
    Boolean fromPlay;
    public TextParse()
    {

    }

    public void ParseText(String text, ArrayList<Team> Teams)
    {
        int i = 0;
        data = text.split(" ");
        while (i != data.length-1);
        {
            if (data[i].toLowerCase().matches("number")) //if the next word is number
            {
                i++;
                if (data[i].toLowerCase().matches("\\d")) //if next word is a digit
                {
                    for (int j = 0; j < stats.length; j++) //read stats
                    {

                    }
                }
            }
            else
                {
                for (int j = 0; j < numbers.length; j++) //check number as word
                    {
                    if (data[i].toLowerCase().matches(numbers[j])) //if next word is a number as word
                        {
                        if (numbers[j] == "twenty")
                            {
                                i++;
                            for (int k=0; k<9; k++)
                                {
                                if (data[i].toLowerCase().matches(numbers[k])) //if next word is twenty, check if number is greater than twenty
                                    {

                                    }
                                }
                            i--;
                            }
                        }
                    else
                        {

                        }
                    }
                }
        }
    }
}
