import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    //main method
    public static void main(String[] args) throws IOException, CsvException {
        Scanner sc = new Scanner(System.in);


        //welcome
        System.out.println("Get recommended three books that I have read!");
        System.out.println("What moods are you looking for?");
        System.out.println ("(Limit input to three words for best results)");

        //read user input
        String moods = sc.nextLine();
        moods = moods.toLowerCase();
        //store user input in an array list
        ArrayList<String> arrayMoods = new ArrayList<>(Arrays.asList(moods.split("[,;\\s]+")));

        //remove any stray spaces
        arrayMoods.removeIf(String::isEmpty);

        //read csv file
        ReadFile readFile = new ReadFile();
        readFile.readFile();

        //recommend books
        BookRecommender bookRecommender = new BookRecommender();
        bookRecommender.recommendBooks(arrayMoods);

    }
}