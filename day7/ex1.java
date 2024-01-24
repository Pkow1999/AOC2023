package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//Overengineered but it is as it is
public class ex1  {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilePath = "day7/Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);
        long totalWin = 0;

        HashMap<Hand, Integer> fiveOfAKind = new HashMap<>();
        HashMap<Hand, Integer> fourOfAKind = new HashMap<>();
        HashMap<Hand, Integer> fullHouse = new HashMap<>();
        HashMap<Hand, Integer> threeOfAKind = new HashMap<>();
        HashMap<Hand, Integer> twoPair = new HashMap<>();
        HashMap<Hand, Integer> onePair = new HashMap<>();
        HashMap<Hand, Integer> highCard = new HashMap<>();
        
         while (reader.hasNextLine()) {
            String[] handAndBid  = reader.nextLine().split("\\s");
            HashMap<Character, Integer> numberOfCardsInHand = new HashMap<>();
            for(int i = 0; i < 5; ++i)
            {

                char card = handAndBid[0].charAt(i);
                numberOfCardsInHand.put(card, numberOfCardsInHand.getOrDefault(card, 0) + 1);
            }
            int max = Collections.max(numberOfCardsInHand.values());
            ArrayList<Character> listOfBestCards = new ArrayList<>();
            for(Map.Entry<Character, Integer> cardToNumber : numberOfCardsInHand.entrySet())
            {
                if(cardToNumber.getValue() == max)
                {
                    listOfBestCards.add(cardToNumber.getKey());
                }
                if(max == 3)
                {
                    if(cardToNumber.getValue() == max - 1)
                    {
                        listOfBestCards.add(cardToNumber.getKey());
                    }
                }
            }
            //System.out.println("MAX: " + max + " " + listOfBestCards.toString());
            if(max == 5)
            {
                //System.out.println("Piątka");
                fiveOfAKind.put(new Hand(handAndBid[0]), Integer.valueOf(handAndBid[1]));
            }
            else if(max == 4)
            {
                //System.out.println("Czwórka");
                fourOfAKind.put(new Hand(handAndBid[0]), Integer.valueOf(handAndBid[1]));
            }
            else if(max == 3 && listOfBestCards.size() == 2)
            {
                //System.out.println("Full House");
                fullHouse.put(new Hand(handAndBid[0]), Integer.valueOf(handAndBid[1]));
            }
            else if(max == 3 && listOfBestCards.size() == 1)
            {
                //System.out.println("Trójka");
                threeOfAKind.put(new Hand(handAndBid[0]), Integer.valueOf(handAndBid[1]));
            }
            else if(max == 2 && listOfBestCards.size() == 2)
            {
                //System.out.println("Dwie Pary");
                twoPair.put(new Hand(handAndBid[0]), Integer.valueOf(handAndBid[1]));
            }
            else if(max == 2 && listOfBestCards.size() == 1)
            {
                //System.out.println("Para");
                onePair.put(new Hand(handAndBid[0]), Integer.valueOf(handAndBid[1]));
            }
            else 
            {
                //System.out.println("Wysoka Karta");
                highCard.put(new Hand(handAndBid[0]), Integer.valueOf(handAndBid[1]));
            }
        }
        reader.close();

        ArrayList<Hand> sortedFive = new ArrayList<>(fiveOfAKind.keySet());
        ArrayList<Hand> sortedFour = new ArrayList<>(fourOfAKind.keySet());
        ArrayList<Hand> sortedFull = new ArrayList<>(fullHouse.keySet());
        ArrayList<Hand> sortedThree = new ArrayList<>(threeOfAKind.keySet());
        ArrayList<Hand> sortedTwoPair = new ArrayList<>(twoPair.keySet());
        ArrayList<Hand> sortedOnePair = new ArrayList<>(onePair.keySet());
        ArrayList<Hand> sortedHighCard = new ArrayList<>(highCard.keySet());

        ArrayList<ArrayList<Hand>> allHands = new ArrayList<>();
        allHands.add(sortedHighCard);
        allHands.add(sortedOnePair);
        allHands.add(sortedTwoPair);
        allHands.add(sortedThree);
        allHands.add(sortedFull);
        allHands.add(sortedFour);
        allHands.add(sortedFive);

        for (ArrayList<Hand> arrayList : allHands) {
            Collections.sort(arrayList);
        }

        ArrayList<HashMap<Hand, Integer>> allHandsMap = new ArrayList<>();
        allHandsMap.add(highCard);
        allHandsMap.add(onePair);
        allHandsMap.add(twoPair);
        allHandsMap.add(threeOfAKind);
        allHandsMap.add(fullHouse);
        allHandsMap.add(fourOfAKind);
        allHandsMap.add(fiveOfAKind);

        int bidCounter = 1;
        for(int i = 0; i < allHands.size(); ++i)
        {
            List<Hand> currentList = allHands.get(i);
            Map<Hand, Integer> currentMap = allHandsMap.get(i);
            for(int j = 0; j < currentList.size(); ++j)
            {
                //System.out.println("REKA: " + currentList.get(j).toString() + " WARTOSC: " + currentMap.get(currentList.get(j)).toString() + " BIDCOUNTER: " + bidCounter);
                totalWin += bidCounter * (int) currentMap.get(currentList.get(j));
                ++bidCounter;
            }
        }
        System.out.println(totalWin);
    }
}

class Card implements Comparable<Card>
{
    private char label;
    private int value;
    private HashMap<Character, Integer> defaultValues = new HashMap<>(){{
        put('A', 12);put('K', 11);put('Q', 10);put('J', 9);put('T', 8);
        put('9', 7);put('8', 6);put('7', 5);put('6', 4);put('5', 3);
        put('4', 2);put('3', 1);put('2', 0);
    }};
    public Card(Character l, Integer v)
    {
        label = l;
        value = v;
    }
    public Card(Character l)
    {
        label = l;
        value = defaultValues.get(l);
    }
    public char getLabel() {
        return label;
    }
    public int getValue() {
        return value;
    }
    @Override
    public int compareTo(Card o) {
        return this.value - o.value;
    }
    @Override
    public String toString() {
        return label + ": " + value;
    }
};
class Hand implements Comparable<Hand>
{
    private ArrayList<Card> cards = new ArrayList<>();
    public Hand(ArrayList<Card> c)
    {
        cards = new ArrayList<>(c);
    }
    public Hand(String c)
    {
        for(int i = 0; i < c.length(); ++i)
        {
            cards.add(new Card(c.charAt(i)));
        }
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    @Override
    public String toString() {
        String returnString = "";
        for (Card card : cards) {
            returnString += card.getLabel();
        }
        return returnString;
    }
    @Override
    public int compareTo(Hand o) {
        for(int i = 0 ; i < 5; ++i)
        {
            if(this.cards.get(i).compareTo(o.cards.get(i)) != 0){
                return this.cards.get(i).compareTo(o.cards.get(i));
            }
        }
        return 0;
    }
}
