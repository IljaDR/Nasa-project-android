package be.idr.nasaproject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "earth_data")
@TypeConverters({Converters.class})
public class EarthData {

    public enum Rating {
        LIKE,
        NEUTRAL,
        DISLIKE
    }

    @PrimaryKey
    @NonNull
    private String identifier;
    private String date;
    private String caption;
    private String imgURL;
    private Rating rating;

    public EarthData(@NonNull String date, @NonNull String identifier, @NonNull String caption, @NonNull String imgURL){
        this.date = date;
        this.identifier = identifier;
        this.caption = caption;
        this.imgURL = imgURL;
        this.rating = Rating.NEUTRAL;
    }

//    public EarthData(@NonNull String date) {
//        this.date = date;
//    }

    @NonNull
    public String getDate(){
        return this.date;
    }

    @NonNull
    public String getIdentifier() {
        return identifier;
    }

    @NonNull
    public String getCaption() {
        return caption;
    }

    public String getImgURL() {
        return imgURL;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating){
        this.rating = rating;
    }
}
