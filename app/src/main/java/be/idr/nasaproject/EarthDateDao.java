package be.idr.nasaproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EarthDateDao {
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
}
