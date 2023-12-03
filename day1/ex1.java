import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Ex1 {
    public static void main(String args[]) throws FileNotFoundException
    {
        ArrayList<Integer> allNumberIntegers = new ArrayList<>();


        String inputFilePath = "Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            ArrayList<Integer> numbers = new ArrayList<>();
            Matcher match = Pattern.compile("\\d").matcher(reader.nextLine());
            while (match.find()) {
                numbers.add(Integer.valueOf(match.group()));
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