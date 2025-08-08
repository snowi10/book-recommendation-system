import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class FilterBooks {

    static ArrayList<String[]> removedBooksList = new ArrayList<>();
    static ArrayList<Integer> removedMoodCounterList = new ArrayList<>();
    static ArrayList<Float> removedStarsList = new ArrayList<>();
    static ArrayList<String> removedFavoritesList = new ArrayList<>();
    public static ArrayList<ArrayList<String>> removedMoodsList = new ArrayList<>();


    //getters
    public static ArrayList<String[]> getRemovedBooksList() {
        return removedBooksList;
    }

    public static ArrayList<Integer> getRemovedMoodCounterList() {
        return removedMoodCounterList;
    }

    public static ArrayList<Float> getRemovedStarsList() {
        return removedStarsList;
    }

    public static ArrayList<String> getRemovedFavoritesList() {
        return removedFavoritesList;
    }
    public static ArrayList<ArrayList<String>> getRemovedMoodsList() {return removedMoodsList;}


    //filter method
    public ArrayList<String[]> filterMethod(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsArray, ArrayList<String>arrayMoods) {
        FilterBooks filterBooks = new FilterBooks();


        //input moods don't match any books
        if(booksRecommended.isEmpty()) {
            System.out.println("Sorry! There are currently no books that are a good match.");
        }
        //filter books by mood count if there are more than three books recommended
        else if(booksRecommended.size() > 3) {
            filterBooks.filterMoods(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray);

            //pick books by each mood if the highest mood count is 1
            if(moodCounterList.getFirst() == 1 && arrayMoods.size() > 1) {
                filterBooks.chooseEach(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, arrayMoods);

            }


            //filter books by star rating if there are still more than three books recommended
            if(booksRecommended.size() > 3) {
                filterBooks.filterStars(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray);
            }


            //filter books by "favorites" tag if there are still more than three books recommended
            if(booksRecommended.size() > 3) {
                filterBooks.filterFavorites(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray);
            }


            //pick three random books if there are still more than three books recommended
            if(booksRecommended.size() > 3) {
                while(booksRecommended.size() > 3) {
                    filterBooks.chooseThree(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray);
                }
            }
        }

        //pick more books if there are less than three books recommended
        if(!booksRecommended.isEmpty() && booksRecommended.size() < 3) {
            chooseMore(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray);
        }

        //make sure at least each mood appears in the recommended list if applicable, regardless of ranking
        chooseOther(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, arrayMoods);



        //print books that were removed
        /*
        for(int i = 0; i < getRemovedBooksList().size(); i++) {
            Books formatRemovedBooks = new Books();
            formatRemovedBooks.stringBooks(getRemovedBooksList().get(i)[0], getRemovedBooksList().get(i)[1], getRemovedBooksList().get(i)[3], getRemovedMoodsList().get(i));
            System.out.println(formatRemovedBooks + " " + getRemovedStarsList().get(i) + " " + getRemovedMoodCounterList().get(i) + " " + getRemovedFavoritesList().get(i) + " " + getRemovedMoodsList().get(i));
        }

        System.out.print("\n");

         */

        return booksRecommended;
    }

    //update removed items
    private static void updateRemovedItems(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsArray, int num) {

       getRemovedBooksList().add(booksRecommended.get(num));
       booksRecommended.remove(num);

       getRemovedMoodCounterList().add(moodCounterList.get(num));
       moodCounterList.remove(num);

       getRemovedStarsList().add(starsList.get(num));
       starsList.remove(num);

       getRemovedFavoritesList().add(favoritesList.get(num));
       favoritesList.remove(num);

       getRemovedMoodsList().add(booksMoodsArray.get(num));
       booksMoodsArray.remove(num);
    }

    private static void addRemovedItems(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsArray, int num) {
        booksRecommended.add(getRemovedBooksList().get(num));
        getRemovedBooksList().remove(num);

        moodCounterList.add(getRemovedMoodCounterList().get(num));
        getRemovedMoodCounterList().remove(num);

        starsList.add(getRemovedStarsList().get(num));
        getRemovedStarsList().remove(num);

        favoritesList.add(getRemovedFavoritesList().get(num));
        getRemovedFavoritesList().remove(num);

        booksMoodsArray.add(getRemovedMoodsList().get(num));
        getRemovedMoodsList().remove(num);

    }

    //sort books by star rating (lowest to highest)
    private void sortStars() {
        for(int i = 0; i < getRemovedStarsList().size(); i++) {
            for(int j = i + 1; j < getRemovedStarsList().size(); j++) {
                if(getRemovedStarsList().get(i) > getRemovedStarsList().get(j)) {
                    Collections.swap(getRemovedMoodCounterList(), i, j);
                    Collections.swap(getRemovedBooksList(), i, j);
                    Collections.swap(getRemovedStarsList(), i, j);
                    Collections.swap(getRemovedFavoritesList(), i, j);
                    Collections.swap(getRemovedMoodsList(), i, j);
                }
            }
        }
    }

    //sort books by mood count (lowest to highest)
    private void sortMoods() {
        for(int i = 0; i < getRemovedMoodCounterList().size(); i++) {
            for(int j = i + 1; j < getRemovedMoodCounterList().size(); j++) {
                if(getRemovedMoodCounterList().get(i) > getRemovedMoodCounterList().get(j)) {
                    Collections.swap(getRemovedMoodCounterList(), i, j);
                    Collections.swap(getRemovedBooksList(), i, j);
                    Collections.swap(getRemovedStarsList(), i, j);
                    Collections.swap(getRemovedFavoritesList(), i, j);
                    Collections.swap(getRemovedMoodsList(), i, j);
                }
            }
        }
    }

    //filter by mood count
    private void filterMoods(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsList) {
        //sort books recommended by mood count (highest to lowest)
        for(int i = 0; i < moodCounterList.size(); i++) {
            for(int j = i + 1; j < moodCounterList.size(); j++) {
                if(moodCounterList.get(i) < moodCounterList.get(j)) {
                    Collections.swap(moodCounterList, i, j);
                    Collections.swap(booksRecommended, i, j);
                    Collections.swap(starsList, i, j);
                    Collections.swap(favoritesList, i, j);
                    Collections.swap(booksMoodsList, i, j);
                }
            }
        }

        //remove all books that do not contain all moods input by user
        for(int i = moodCounterList.size() - 1; i > -1; i--) {


            if(moodCounterList.get(i) < moodCounterList.getFirst()) {
                updateRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsList, i);
            }
        }


        sortStars();

        sortMoods();

    }

    //filter by star rating
    private void filterStars(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsArray ) {

        //remove all books that are lower than the highest rated book
        for(int i = starsList.size() - 1; i > -1; i--) {
            if (starsList.get(i) < starsList.getFirst()){
                updateRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, i);

            }
        }


    }

    //filter by "favorites" tag
    private void filterFavorites(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsArray) {

        for(int i = 0; i < favoritesList.size(); i++) {
            String[] tags = favoritesList.get(i).split(", ");
            boolean favorites = false;
            for(String count : tags){
                if(count.equals("favorites")) {
                    favorites = true;
                    break;
                }

            }

            //remove all books that are not tagged as "favorites"
            if(!favorites) {
                updateRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, i);

                i -= 1;
            }
        }


    }

    //choose one book for each mood if applicable
    private void chooseEach(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsArray, ArrayList<String> arrayMoods) {
        int counter = 0;

        //match each mood input by user to one book
        if(booksRecommended.size() > 3) {
            for (String arrayMood : arrayMoods) {
                for (int j = 0; j < booksMoodsArray.size(); j++) {
                    if (arrayMood.equals(booksMoodsArray.get(j).getFirst())) {
                        booksMoodsArray.add(booksMoodsArray.get(j));
                        booksMoodsArray.remove(j);

                        booksRecommended.add(booksRecommended.get(j));
                        booksRecommended.remove(j);

                        moodCounterList.add(moodCounterList.get(j));
                        moodCounterList.remove(j);

                        starsList.add(starsList.get(j));
                        starsList.remove(j);

                        favoritesList.add(favoritesList.get(j));
                        favoritesList.remove(j);

                        counter++;

                        break;
                    }
                }
            }
        }

        while(booksRecommended.size() !=  counter) {
            updateRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, 0);
        }

        sortStars();

    }

    //choose three books out of the list of books recommended
    private void chooseThree(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsArray) {

        Random rand = new Random();
        int randNum = rand.nextInt(0, booksRecommended.size());

        updateRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, randNum);
    }

    //choose more books
    private void chooseMore(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsArray) {


        //when there was only one book that was removed
        if(getRemovedBooksList().size() == 1) {
            //add book and all of its description to the recommended list
            addRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, 0);
        }
        //when there were only 2 books that were removed
        else if(getRemovedBooksList().size() == 2 && booksRecommended.size() < 2) {
            //add both books that were removed if the recommendation list has less than 2 books
            addRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, 0);

            addRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, 0);
        }
        //when there were 3+ books removed
        else {
            while(booksRecommended.size() < 3 && !getRemovedBooksList().isEmpty()) {
                addRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, getRemovedBooksList().size() - 1);
            }
        }

    }

    //make sure each mood input by user is applied in the recommended list if applicable, regardless of ranking
    private void chooseOther(ArrayList<String[]> booksRecommended, ArrayList<Integer> moodCounterList, ArrayList<Float> starsList, ArrayList<String> favoritesList, ArrayList<ArrayList<String>> booksMoodsArray, ArrayList<String> arrayMoods) {
        int counter = booksMoodsArray.size() - 1;


        //check to see if the moods input by user is in the recommended list
        for(String inputMoods : arrayMoods) {
            boolean moods = false;

            search:
            for(ArrayList<String> bookMoodsList : booksMoodsArray) {
                for(String bookMood : bookMoodsList) {
                    if(inputMoods.equals(bookMood)) {
                        moods = true;

                        break search;
                    }
                }
            }

            //if one or more of the moods input by user is not in the recommended list
            //add a book for each of the moods if there is a book applicable
            if(!moods && counter > -1) {

                search:
                for(int i = getRemovedMoodsList().size() - 1; i > -1 ; i--) {
                    for(int j = 0; j < getRemovedMoodsList().get(i).size(); j++) {
                        if(inputMoods.equals(getRemovedMoodsList().get(i).get(j))) {
                            updateRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, counter);
                            addRemovedItems(booksRecommended, moodCounterList, starsList, favoritesList, booksMoodsArray, i);

                            counter--;
                            break search;
                        }
                    }
                }

            }

        }
    }
}
