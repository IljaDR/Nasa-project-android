package be.idr.nasaproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "earth_date")
public class EarthDate {

    @PrimaryKey
    @NonNull
    private String date;

    public EarthDate(@NonNull String date){
        this.date = date;
    }

    @NonNull
    public String getDate(){
        return this.date;
    }
}
