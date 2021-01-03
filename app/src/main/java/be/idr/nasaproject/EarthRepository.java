package be.idr.nasaproject;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class EarthRepository {

    private EarthDateDao earthDateDao;
    private LiveData<List<EarthData>> allDates;

    EarthRepository(Application application){
        EarthRoomDatabase db = EarthRoomDatabase.getDatabase(application);
        earthDateDao = db.earthDateDao();
        allDates = earthDateDao.getDateByRecency();
    }

    LiveData<List<EarthData>> getAllDates(){
        return allDates;
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
