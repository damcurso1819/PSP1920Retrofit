package org.izv.ad.psp1920retrofit.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.izv.ad.psp1920retrofit.model.Repository;
import org.izv.ad.psp1920retrofit.model.data.Type;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void addType(Type type) {
        repository.add(type);
    }

    public List<Type> getTypeList() {
        return repository.getTypeList();
    }

    public LiveData<List<Type>> getLiveTypeList() {
        return repository.getLiveTypeList();
    }

    public void setUrl(String url) {
        repository.setUrl(url);
    }
}
