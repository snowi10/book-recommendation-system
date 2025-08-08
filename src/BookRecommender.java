import org.apache.commons.text.WordUtils;
import java.util.ArrayList;

public class BookRecommender {

    private int counter = 0;

    public int bookCounter() {
        counter++;

        return counter;
    }

    public void recommendBooks(ArrayList<String> arrayMoods){

        //retrieves all the books I have read
        ReadFile readFile = new ReadFile();
        ArrayList<String[]> booksList = readFile.sortFile();

        ArrayList<String[]> booksRecommended = new ArrayList<>();
        ArrayList<Integer> moodCounterList = new ArrayList<>();
        ArrayList<Float> starsList = new ArrayList<>();
        ArrayList<String> favoritesList = new ArrayList<>();
        ArrayList<ArrayList<String>> booksMoodsArray = new ArrayList<>();
        String[] bookMoodsList;
        StringBuilder none = new StringBuilder("Unable to include: ");

        //traverse through list of books I've read
        for(int i = 1; i < booksList.size(); i++) {

            //create new instance of BookRecommender for a particular book
            BookRecommender newCounter = new BookRecommender();

            //create new array of moods for a particular book
            ArrayList<String> equalMoods = new ArrayList<>();

            //put all moods of a particular book into an array
            bookMoodsList = booksList.get(i)[2].split(", ");

            //traverse through all moods input by user
            for(String moodsInput : arrayMoods) {

                //traverse through all moods of a particular book
                for(String bookMoods : bookMoodsList) {

                    //compare moods input by user to moods of a particular book
                    if (bookMoods.equals(moodsInput)) {

                        if(!equalMoods.contains(moodsInput)) {
                            equalMoods.add(moodsInput);
                            counter = newCounter.bookCounter();
                        }


                        //add a particular book if book is not already in the list of books recommended
                        if(!booksRecommended.contains(booksList.get(i))) {
                            booksRecommended.add(booksList.get(i));
                        }
                    }
                }
            }


            //add the description of recommended books to moodCounterList, favoritesList, and starsList
            if(booksRecommended.contains(booksList.get(i))) {

                moodCounterList.add(counter);
                booksMoodsArray.add(equalMoods);

                if(booksList.get(i)[3].isEmpty()) {
                    starsList.add(0.0f);
                }
                else {
                    starsList.add(Float.parseFloat(booksList.get(i)[3]));
                }

                favoritesList.add(booksList.get(i)[4]);
            }
        }

        //suggest three books from the list of recommended books
        FilterBooks filterBooks = new FilterBooks();
        booksRecommended = filterBooks.filterMethod(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, arrayMoods);

        for(int i = 0; i < booksRecommended.size(); i++) {
            Books formatBooksRecommended = new Books();
            formatBooksRecommended.stringBooks(booksRecommended.get(i)[0], booksRecommended.get(i)[1], booksRecommended.get(i)[3], booksMoodsArray.get(i));
            System.out.println(formatBooksRecommended);
        }


        //if no books match certain moods input by user
        ArrayList<String> unusedMoods = new ArrayList<>();
        if(!booksRecommended.isEmpty()) {
            for(String moodsInput : arrayMoods) {
                boolean moodBoolean = false;
                for(ArrayList<String> moodsList : booksMoodsArray){
                    if (moodsList.contains(moodsInput)) {
                        moodBoolean = true;
                        break;
                    }
                }

                if(!moodBoolean && !unusedMoods.contains(moodsInput)) {
                    unusedMoods.add(moodsInput);

                    if(none.toString().equals("Unable to include: ")) {
                        none.append(WordUtils.capitalize(moodsInput));
                    }
                    else {
                        none.append(", ").append(WordUtils.capitalize(moodsInput));
                    }


                }
            }


            if(!none.toString().equals("Unable to include: ")) {
                System.out.println("\n" + none);
            }

        }


    }
}
