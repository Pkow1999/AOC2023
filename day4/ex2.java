package day4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class ex2 {
    public static void main(String args[]) throws FileNotFoundException
    {
        String inputFilePath = "day4/Input";

        ArrayList<Integer> listOfWinningNumbers = new ArrayList<>();
        ArrayList<Integer> listOfMyNumbers = new ArrayList<>();
        AbstractList<Integer> currentList;
        Map<Integer, Integer> copiesOfTheCards = new HashMap<>();
        for(int i = 1; i <= 193; ++i)//we got 1 copy of each card
            copiesOfTheCards.put(i, 1);

        int cardNr = 0;
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
            int cards = 0;//which cards do i get copies of
            for (int winningNumber : listOfWinningNumbers) {
                if(listOfMyNumbers.contains(winningNumber))
                {
                    ++cards;
                }
            }
            for(int winnedCopies = cardNr + 1; winnedCopies <= cardNr + cards; ++winnedCopies)
            {
                int numberOfCopies = copiesOfTheCards.get(cardNr);
                int currentNumberOfCopies = copiesOfTheCards.get(winnedCopies);

                copiesOfTheCards.put(winnedCopies, currentNumberOfCopies + numberOfCopies);
            }
        }
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : copiesOfTheCards.entrySet()) {
            System.out.println("Card " + entry.getKey() + ": " + entry.getValue().toString());
            sum += entry.getValue();
        }
        System.out.println(sum);

    }
}