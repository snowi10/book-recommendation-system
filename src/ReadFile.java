import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ReadFile {

    private static ArrayList<String[]> booksList = new ArrayList<>();

    public static ArrayList<String[]> getBooksList() {
        return booksList;
    }

    //access Books.csv
    public void readFile() throws IOException, CsvException {

        //reads csv file
        try(CSVReader csvReader = new CSVReaderBuilder(new FileReader("src/resources/Books.csv"))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(',')
                        .build())
                .build()) {

            String[] line;
            String[] booksArray;

            while((line = csvReader.readNext()) != null) {

                //store a book's description in an array
                booksArray = line;

                //store the array into a list of books
                getBooksList().add(booksArray);

            }
        }
    }

    public ArrayList<String[]> sortFile() {
       ArrayList<String[]> modifiedBooksList = new ArrayList<>();

       //remove books I haven't read yet
       getBooksList().removeIf(array -> Arrays.asList(array).contains("to-read"));

       //convert array string of book descriptions into an array list
       for(String[] book : getBooksList()) {

           ArrayList<String> descriptionList = getStrings(book);

           //add modified book descriptions into a new list
           String[] bookArray = descriptionList.toArray(String[]::new);
           modifiedBooksList.add(bookArray);
       }

       //sort books by star rating (highest to lowest)
        for(int i = 1; i < modifiedBooksList.size(); i++) {
            for(int j = i + 1; j < modifiedBooksList.size(); j++) {
                if((modifiedBooksList.get(i)[3].isEmpty()) || (!modifiedBooksList.get(j)[3].isEmpty()) && (Float.parseFloat(modifiedBooksList.get(i)[3]) < Float.parseFloat(modifiedBooksList.get(j)[3]))) {
                    Collections.swap(modifiedBooksList, i, j);
                }
            }
        }


        //assign original list to new modified list
        booksList = modifiedBooksList;
        return modifiedBooksList;
    }

    private static ArrayList<String> getStrings(String[] book) {
        ArrayList<String> descriptionList = new ArrayList<>(Arrays.asList(book));

        //remove unnecessary book descriptions
        descriptionList.remove(22);
        descriptionList.remove(20);
        descriptionList.remove(19);
        descriptionList.remove(18);
        descriptionList.remove(16);
        descriptionList.remove(15);
        descriptionList.remove(14);
        descriptionList.remove(13);
        descriptionList.remove(12);
        descriptionList.remove(11);
        descriptionList.remove(9);
        descriptionList.remove(8);
        descriptionList.remove(7);
        descriptionList.remove(6);
        descriptionList.remove(5);
        descriptionList.remove(4);
        descriptionList.remove(3);
        descriptionList.remove(2);
        return descriptionList;
    }
}
