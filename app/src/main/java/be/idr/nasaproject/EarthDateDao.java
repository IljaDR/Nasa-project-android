package be.idr.nasaproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EarthDateDao {
    @Query("SELECT * FROM earth_date ORDER BY date ASC")
    LiveData<List<EarthDate>> getDateByRecency();

    @Query("SELECT * FROM earth_date ORDER BY date ASC LIMIT 1")
    LiveData<List<EarthDate>> getMostRecentDate();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EarthDate date);

    @Query("DELETE FROM earth_date")
    void deleteAll();
}
