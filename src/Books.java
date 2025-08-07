import org.apache.commons.text.WordUtils;
import java.util.ArrayList;

public class Books {
    private String title;
    private String author;
    private String myRating;
    private String moodsString;


    public void stringBooks(String title, String author, String myRating, ArrayList<String> equalMoods) {
        this.title = title;
        this.author = author;

        //check if my rating is not empty
        if (myRating.isEmpty()) {
            this.myRating = "N/A";
        } else {
            this.myRating = myRating + " stars";
        }

        //print moods that are a match of a particular book
        StringBuilder moodsStringBuilder = new StringBuilder();
        for(int i = 0; i < equalMoods.size(); i++) {
            if(i == equalMoods.size() - 1) {
                moodsStringBuilder.append(equalMoods.get(i));
            }
            else {
                moodsStringBuilder.append(equalMoods.get(i)).append(", ");
            }
        }
        this.moodsString = WordUtils.capitalize(moodsStringBuilder.toString());

    }

    @Override
    public String toString() {
        return moodsString + ": " + this.title + " by " + this.author + " | My Rating: " + this.myRating;
    }
}
