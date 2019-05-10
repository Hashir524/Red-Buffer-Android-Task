package android.rb.test.task.viewmodel;

import android.app.Application;
import android.rb.test.task.repository.MapRepository;
import android.rb.test.task.service.models.AllUsersLocationResponse;
import android.rb.test.task.service.models.LocationList;
import android.rb.test.task.service.models.UploadUserLocationResponseModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MapViewModel extends AndroidViewModel {

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    //
    public LiveData<UploadUserLocationResponseModel> uploadUserLocationRequest(String username, String email, Double lat, Double lng){
        return MapRepository.getInstance().uploadUserLocationResponseRequest(username,email,lat,lng);
    }

    public LiveData<List<AllUsersLocationResponse>> fetchAllUserLocationResponse(){
        return MapRepository.getInstance().fetchAllUserLocationRequest();
    }
}
