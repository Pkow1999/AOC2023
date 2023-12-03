import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ex2 {
    
    public static void main(String args[]) throws FileNotFoundException
    {
        ArrayList<Integer> allNumberIntegers = new ArrayList<>();


        Dictionary<String, Integer> wordNumber= new Hashtable<>();
        wordNumber.put("one", 1);
        wordNumber.put("two", 2);// 2 -> 1
        wordNumber.put("three", 3);
        wordNumber.put("four", 4);
        wordNumber.put("five", 5);
        wordNumber.put("six", 6);
        wordNumber.put("seven", 7);// 7 -> 9
        wordNumber.put("eight", 8);// 8 -> 2
        wordNumber.put("nine", 9);// 9 -> 8
        wordNumber.put("zero", 0);// 0 -> 1

        String regex = "(?=(\\d";
        Enumeration<String> key = wordNumber.keys();
        while (key.hasMoreElements()) {
            regex += "|" +key.nextElement();
        }
        regex += "))";
        String inputFilePath = "Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            ArrayList<Integer> numbers = new ArrayList<>();
            Matcher match = Pattern.compile(regex).matcher(reader.nextLine());
            while (match.find()) {
                if(match.group(1).length() > 1)
                {
                    numbers.add(wordNumber.get(match.group(1)));
                }
                else 
                {
                    numbers.add(Integer.valueOf(match.group(1)));
                }

            }
            allNumberIntegers.add(numbers.get(0) * 10 + numbers.get(numbers.size() - 1));
        }
        reader.close();
        int sum = 0;
        for (int numberInt : allNumberIntegers) {
            sum += numberInt;
        }
        System.out.println(sum);
    }
}
