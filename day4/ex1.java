package day4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class ex1 {
    public static void main(String args[]) throws FileNotFoundException
    {
        String inputFilePath = "day4/Input";

        ArrayList<Integer> listOfWinningNumbers = new ArrayList<>();
        ArrayList<Integer> listOfMyNumbers = new ArrayList<>();
        AbstractList<Integer> currentList;
        int sumOfAll = 0;

        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);
        Matcher match;
        while(reader.hasNextLine())
        {
            ++cardNr;
            listOfWinningNumbers.clear();
            listOfMyNumbers.clear();

            String line = reader.nextLine();
            match = Pattern.compile("\\d+\\s*\\|*").matcher(line);
            currentList = listOfWinningNumbers;
            while (match.find()) 
            {
                if(match.group().contains("|"))
                {
                    currentList.remove(0);
                    currentList.add(Integer.valueOf(match.group().substring(0, match.group().length() - 2)));
                    currentList = listOfMyNumbers;
                }
                else
                {
                    currentList.add(Integer.valueOf(match.group().replaceAll("\\s+","")));
                }
            }
            int winningSum = 0;
            for (int winningNumber : listOfWinningNumbers) {
                if(listOfMyNumbers.contains(winningNumber))
                {
                    ++winningSum;
                }
            }
            if(winningSum > 0)
                sumOfAll+=Math.pow(2, winningSum - 1);
        }
        System.out.println(sumOfAll);

    }
}