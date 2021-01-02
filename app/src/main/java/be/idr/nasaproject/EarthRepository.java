package be.idr.nasaproject;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class EarthRepository {

    private EarthDateDao earthDateDao;
    private LiveData<List<EarthDate>> allDates;

    EarthRepository(Application application){
        EarthRoomDatabase db = EarthRoomDatabase.getDatabase(application);
        earthDateDao = db.earthDateDao();
        allDates = earthDateDao.getDateByRecency();
    }

    LiveData<List<EarthDate>> getAllDates(){
        return allDates;
    }

    void insert(EarthDate earthDate){
        EarthRoomDatabase.databaseWriteExecutor.execute(() -> {
            earthDateDao.insert(earthDate);
        });
    }

    void insertList(List<EarthDate> earthDates){
        for(EarthDate earthDate: earthDates){
            EarthRoomDatabase.databaseWriteExecutor.execute(() -> {
                earthDateDao.insert(earthDate);
            });
        }
    }
}
