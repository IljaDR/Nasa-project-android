package be.idr.nasaproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "earth_data")
public class EarthData {

    @PrimaryKey
    @NonNull
    private String date;
    private String identifier;
    private String caption;
    private String imgURL;

    public EarthData(@NonNull String date, @NonNull String identifier, @NonNull String caption, @NonNull String imgURL){
        this.date = date;
        this.identifier = identifier;
        this.caption = caption;
        this.imgURL = imgURL;
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
}
