package be.idr.nasaproject.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.List;

@Dao
@TypeConverters({Converters.class})
public interface EarthDataDao {
    @Query("SELECT * FROM earth_data ORDER BY date DESC")
    LiveData<List<EarthData>> getDateByRecency();

    @Query("SELECT * FROM earth_data ORDER BY date ASC LIMIT 1")
    LiveData<List<EarthData>> getMostRecentDate();

    @Query("SELECT * FROM earth_data ORDER BY date DESC LIMIT 1")
    LiveData<List<EarthData>> getOldestDate();

    @Query("SELECT COUNT(*) FROM earth_data")
    LiveData<Integer> getCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EarthData date);

    @Query("DELETE FROM earth_data")
    void deleteAll();

    @Query("UPDATE EARTH_DATA SET RATING =  :rating WHERE identifier = :identifier")
    void addRating(EarthData.Rating rating, String identifier);
}
