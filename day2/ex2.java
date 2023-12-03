import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class ex2 {
    public static void main(String args[]) throws FileNotFoundException
    {
        ArrayList<Integer> listOfRed = new ArrayList<>();
        ArrayList<Integer> listOfBlue = new ArrayList<>();
        ArrayList<Integer> listOfGreen = new ArrayList<>();
        int sum = 0;;
        
        String inputFilePath = "Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);
        Matcher match;
        while (reader.hasNextLine()) 
        {
            String line = reader.nextLine();
            match = Pattern.compile("(\\d+) red").matcher(line);
            while (match.find()) 
            {
                listOfRed.add(Integer.valueOf(match.group(1)));
            }
            match = Pattern.compile("(\\d+) green").matcher(line);
            while (match.find()) 
            {
                listOfGreen.add(Integer.valueOf(match.group(1)));
            }
            match = Pattern.compile("(\\d+) blue").matcher(line);
            while (match.find()) {
                listOfBlue.add(Integer.valueOf(match.group(1)));
            }
            System.out.println(line);
            sum += Collections.max(listOfRed) * Collections.max(listOfGreen) * Collections.max(listOfBlue);

            listOfBlue.clear();;
            listOfRed.clear();
            listOfGreen.clear();
        }
        reader.close();
        System.out.println(sum);
    }
}
