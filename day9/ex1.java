package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex1 {
    public static void main(String[] args) throws FileNotFoundException{
        String inputFilePath = "Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);

        ArrayList<Integer> resultsList = new ArrayList<>();
        while(reader.hasNextLine())
        {
            String line = reader.nextLine();
            Matcher matchNumber = Pattern.compile("-*\\d+").matcher(line);
            ArrayList<ArrayList<Integer>> allArraysOfNumber = new ArrayList<>();
            ArrayList<Integer> startingNumbers = new ArrayList<>();
            while (matchNumber.find())
            {
                startingNumbers.add( Integer.valueOf(matchNumber.group()) );
            }
            allArraysOfNumber.add(startingNumbers);
            while (true) {
                int counter = 0;
                for(int i = 0; i < allArraysOfNumber.get(allArraysOfNumber.size() - 1).size(); ++i)
                {
                    if(allArraysOfNumber.get(allArraysOfNumber.size() - 1).get(i) != 0)
                    {
                        break;
                    }
                    else
                    {
                        counter++;
                    }
                }
                if(counter == allArraysOfNumber.get(allArraysOfNumber.size() - 1).size())
                {
                    allArraysOfNumber.get(allArraysOfNumber.size() - 1).add(0);
                    break;
                }
                else
                {
                    ArrayList<Integer> newDataset = new ArrayList<>();
                    int first = allArraysOfNumber.get(allArraysOfNumber.size() - 1).get(0);
                    for(int i = 1; i < allArraysOfNumber.get(allArraysOfNumber.size() - 1).size(); ++i)
                    {
                        int second = allArraysOfNumber.get(allArraysOfNumber.size() - 1).get(i);
                        newDataset.add(second - first);
                        first = second;
                    }
                    allArraysOfNumber.add(newDataset);
                }
            }
            for(int i = allArraysOfNumber.size() - 2; i > -1; --i)//-2 bo bierzemy przed ostatnia - ostatnia i tak bedzie pelna zer
            {
                int last = allArraysOfNumber.get(i).get(allArraysOfNumber.get(i).size() - 1);
                int secondLast = allArraysOfNumber.get(i + 1).get(allArraysOfNumber.get(i + 1).size() - 1);
                allArraysOfNumber.get(i).add(last + secondLast);
            }
            resultsList.add(allArraysOfNumber.get(0).get(allArraysOfNumber.get(0).size() - 1));
        }
        reader.close();
        System.out.println(resultsList.toString());
        int result = 0;
        for (int resultNr : resultsList) {
            result += resultNr;
        }
        System.out.println(result);
    }

}
