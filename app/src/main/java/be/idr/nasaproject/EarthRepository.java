package be.idr.nasaproject;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class EarthRepository {

    private EarthDateDao earthDateDao;
    private LiveData<List<EarthData>> allDates;
    private LiveData<Integer> count;

    EarthRepository(Application application){
        EarthRoomDatabase db = EarthRoomDatabase.getDatabase(application);
        earthDateDao = db.earthDateDao();
        allDates = earthDateDao.getDateByRecency();
        count = earthDateDao.getCount();
    }

    LiveData<List<EarthData>> getAllDates(){
        return allDates;
    }

    LiveData<Integer> getDateCount(){
        return count;
    }

    void insert(EarthData earthData){
        EarthRoomDatabase.databaseWriteExecutor.execute(() -> {
            earthDateDao.insert(earthData);
        });
    }

    void insertList(List<EarthData> earthData){
        for(EarthData data : earthData){
            EarthRoomDatabase.databaseWriteExecutor.execute(() -> {
                earthDateDao.insert(data);
            });
        }
    }
}
