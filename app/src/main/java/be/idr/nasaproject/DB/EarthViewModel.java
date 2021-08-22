package be.idr.nasaproject.DB;

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

    public LiveData<List<EarthData>> getAllDates(){
        return allDates;
    }

    public LiveData<Integer> logDateCount(){
        return repository.getDateCount();
    }

    public void insert(EarthData earthData){
        repository.insert(earthData);
    }

    public void addRating(EarthData.Rating rating, String identifier){
        repository.addRating(rating, identifier);
    }
}
