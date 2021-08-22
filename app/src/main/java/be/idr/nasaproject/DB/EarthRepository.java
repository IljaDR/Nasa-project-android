package be.idr.nasaproject.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EarthRepository {

    private EarthDataDao earthDataDao;
    private LiveData<List<EarthData>> allDates;
    private LiveData<Integer> count;

    EarthRepository(Application application){
        EarthRoomDatabase db = EarthRoomDatabase.getDatabase(application);
        earthDataDao = db.earthDateDao();
        allDates = earthDataDao.getDateByRecency();
        count = earthDataDao.getCount();
    }

    LiveData<List<EarthData>> getAllDates(){
        return allDates;
    }

    LiveData<Integer> getDateCount(){
        return count;
    }

    void insert(EarthData earthData){
        EarthRoomDatabase.databaseWriteExecutor.execute(() -> {
            earthDataDao.insert(earthData);
        });
    }

    void insertList(List<EarthData> earthData){
        for(EarthData data : earthData){
            EarthRoomDatabase.databaseWriteExecutor.execute(() -> {
                earthDataDao.insert(data);
            });
        }
    }

    void addRating(EarthData.Rating rating, String identifier){
        EarthRoomDatabase.databaseWriteExecutor.execute(() -> {
            earthDataDao.addRating(rating, identifier);
        });
    }
}
