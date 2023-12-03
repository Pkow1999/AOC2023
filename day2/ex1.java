import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class ex1 {
    public static void main(String args[]) throws FileNotFoundException
    {
        int sumOfGoodGames = 0;

        boolean error;
        int currentGame = 0;

        String inputFilePath = "Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);
        Matcher match;
        while (reader.hasNextLine()) 
        {
            ++currentGame;
            error = false;
            String line = reader.nextLine();
            match = Pattern.compile("(\\d+) red").matcher(line);
            System.out.println("red:");
            while (match.find()) 
            {
                System.out.println(match.group(1));
                if(Integer.valueOf(match.group(1)) > 12){
                    error = true;
                    break;
                }
            }
            match = Pattern.compile("(\\d+) green").matcher(line);
            System.out.println("green:");
            while (match.find()) 
            {
                System.out.println(match.group(1));
                if(Integer.valueOf(match.group(1)) > 13){
                    error = true;
                    break;
                }
            }
            match = Pattern.compile("(\\d+) blue").matcher(line);
            System.out.println("blue:");
            while (match.find()) {
                System.out.println(match.group(1));
                if(Integer.valueOf(match.group(1)) > 14){
                    error = true;
                    break;
                }
            }
            if(!error){
                sumOfGoodGames += currentGame;
            }
        }
        reader.close();
        System.out.println(sumOfGoodGames);
    }
}
