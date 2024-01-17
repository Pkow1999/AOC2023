package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex1 {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilePath = "day6/Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);

        String Time = reader.nextLine();
        String Distance = reader.nextLine();
        Matcher matchTime = Pattern.compile("\\d+").matcher(Time);
        Matcher matchDistance = Pattern.compile("\\d+").matcher(Distance);
        
        int result = 1;
        while(matchTime.find() && matchDistance.find())
        {
            int timeOfRace = Integer.valueOf(matchTime.group());
            int distanceToBeat = Integer.valueOf(matchDistance.group());
            int counter = 0;
            int previousDistance = 0;

            for (int speed = 1; speed <= timeOfRace/2; ++speed)//mozna o polowe bo pozniej bede miec takie same wyniki tylko odwrocone
            {
                int newDistance = (timeOfRace - speed) * speed;
                
                // if(newDistance > previousDistance){
                //     previousDistance = newDistance;
                // }
                // else if(newDistance == previousDistance)
                // {
                //     counter *= 2;
                //     break;
                // }
                // else
                // {
                //     counter = counter * 2 - 1;
                //     break;
                // }                

                if(newDistance > distanceToBeat){
                    counter = counter + 2; //no i mamy podwojne wyniki
                }
            }
            counter = counter - (timeOfRace + 1)%2;//jesli wyscig mial parzysty czas to odejmujemy jeden bo mamy o jednego dubla za duzo [liczba wychodzi nieparzysta] 
            System.out.println(counter);
            result *= counter;
        }
        System.out.println(result);
        reader.close();
    }
}
