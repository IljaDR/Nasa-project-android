package be.idr.nasaproject.DB;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static String fromEnum(EarthData.Rating rating){
        return rating.name();
    }

    @TypeConverter
    public static EarthData.Rating stringToEnum(String rating){
        return EarthData.Rating.valueOf(rating);
    }
}
