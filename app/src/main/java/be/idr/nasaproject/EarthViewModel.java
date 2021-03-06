package be.idr.nasaproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EarthViewModel extends AndroidViewModel {

    private EarthRepository repository;
    private final LiveData<List<EarthData>> allDates;

    public EarthViewModel(@NonNull Application application) {
        super(application);
        repository = new EarthRepository(application);
        allDates = repository.getAllDates();
    }

    LiveData<List<EarthData>> getAllDates(){
        return allDates;
    }

    void insert(EarthData earthData){
        repository.insert(earthData);
    }
}
