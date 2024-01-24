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
public class ex2  {
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

                char cardLabel = handAndBid[0].charAt(i);
                numberOfCardsInHand.put( cardLabel, numberOfCardsInHand.getOrDefault(cardLabel, 0) + 1);
            }
            int max = Collections.max(numberOfCardsInHand.values());
            ArrayList<Character> listOfBestCards = new ArrayList<>();
            boolean jokerIsNotDominating = false;
            int numberOfJokers = 0;
            for(Map.Entry<Character, Integer> cardToNumber : numberOfCardsInHand.entrySet())
            {
                if(cardToNumber.getKey() == 'J')
                {
                    numberOfJokers = cardToNumber.getValue();
                    if(cardToNumber.getValue() != max)
                    {
                        jokerIsNotDominating = true;//joker is not the biggest number in set
                    }
                }
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
            System.out.println("MAX: " + max + " " + listOfBestCards.toString());
            if(jokerIsNotDominating)
            {
                max += numberOfJokers;//flat increase
            }
            else if(!jokerIsNotDominating && numberOfJokers != 0)
            {
                //joker is in set
                if(numberOfJokers == 4){//four of a kind
                    max += 1;//just get the last one free and get 5
                }
                else if(numberOfJokers == 3 && listOfBestCards.size() == 2){//fullhouse
                    max += 2;//3 jokers + 1 pair, just change into 5
                }
                else if(numberOfJokers == 3 && listOfBestCards.size() == 1){//three of a kind

                    max += 1;//3 jokers, lets get random card to make 4

                }
                else if(numberOfJokers == 2 && listOfBestCards.size() == 2){//two pairs

                    max += 2;//pair of jokers + random pair -> lets get 4 jokers
                }
                else if(numberOfJokers == 2 && listOfBestCards.size() == 1){//one pair

                    max += 1;//only pair of jokers, lets get random card to make
                }
                else if (numberOfJokers == 1){
                    max += 1;//HighCard joker
                }

            }
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
            else if(max == 3 && listOfBestCards.size() != 2)
            {
                //System.out.println("Trójka");
                threeOfAKind.put(new Hand(handAndBid[0]), Integer.valueOf(handAndBid[1]));
            }
            else if(max == 2 && listOfBestCards.size() == 2)
            {
                //System.out.println("Dwie Pary");
                twoPair.put(new Hand(handAndBid[0]), Integer.valueOf(handAndBid[1]));
            }
            else if(max == 2 && listOfBestCards.size() != 2)
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
                System.out.println("REKA: " + currentList.get(j).toString() + " WARTOSC: " + currentMap.get(currentList.get(j)).toString() + " BIDCOUNTER: " + bidCounter);
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
        put('A', 13);put('K', 12);put('Q', 11);put('J', 0);put('T', 9);
        put('9', 8);put('8', 7);put('7', 6);put('6', 5);put('5', 4);
        put('4', 3);put('3', 2);put('2', 1);
    }};
    public Card(Character l, Integer v)
    {
        label = l;
        value = v;
    }
    public Card(Character l)
    {
        label = l;
        value = defaultValues.getOrDefault(l, -1);
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
