package be.idr.nasaproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EarthViewModel extends AndroidViewModel {

    private EarthRepository repository;
    private final LiveData<List<EarthDate>> allDates;

    public EarthViewModel(@NonNull Application application) {
        super(application);
        repository = new EarthRepository(application);
        allDates = repository.getAllDates();
    }

    LiveData<List<EarthDate>> getAllDates(){
        return allDates;
    }

    void insert(EarthDate earthDate){
        repository.insert(earthDate);
    }
}
