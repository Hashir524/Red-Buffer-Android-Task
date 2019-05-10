package android.rb.test.task.repository;

import android.rb.test.task.service.models.AllUsersLocationResponse;
import android.rb.test.task.service.models.LocationList;
import android.rb.test.task.service.models.UploadUserLocationResponseModel;
import android.rb.test.task.service.remote.APIService;
import android.rb.test.task.service.remote.ApiUtils;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapRepository {

    private APIService apiService;
    private static MapRepository mapRepository;

    public synchronized static MapRepository getInstance(){
        if(mapRepository == null){
            if(mapRepository == null){

                mapRepository = new MapRepository();
            }
        }
        return mapRepository;
    }

    //Method to access live uploadUserLocation Object
    //Calling UploadUserLocation Api Here
    public LiveData<UploadUserLocationResponseModel> uploadUserLocationResponseRequest(String username, String email, Double lat, Double lng){
        final MutableLiveData<UploadUserLocationResponseModel> uploadUserLocationResponse = new MutableLiveData<>();
        apiService = ApiUtils.getAPIService();

        //Creating Multipart Request
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("email", email)
                .addFormDataPart("lat", String.valueOf(lat))
                .addFormDataPart("lng", String.valueOf(lng))
                .build();

        apiService.uploadUserLocation(requestBody).enqueue(new Callback<UploadUserLocationResponseModel>() {
            @Override
            public void onResponse(Call<UploadUserLocationResponseModel> call, Response<UploadUserLocationResponseModel> response) {
                if(response.isSuccessful()){
                    uploadUserLocationResponse.setValue(response.body());
                    Log.d("Response", "onResponse: " + response.message());
                    Log.d("Response", "onResponse: " + response.code());
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("Response", "onResponse: " + jObjError.getString("message"));
                        uploadUserLocationResponse.setValue(response.body());
                    }catch (Exception e) {
                        Log.d("Response", "onResponse: " + e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<UploadUserLocationResponseModel> call, Throwable t) {

            }
        });

        return uploadUserLocationResponse;
    }

    //Method to access live uploadUserLocation Object
    //Calling UploadUserLocation Api Here
    public LiveData<List<AllUsersLocationResponse>> fetchAllUserLocationRequest(){
        final MutableLiveData<List<AllUsersLocationResponse>> fetchAllUserLocationResponse = new MutableLiveData<>();
        //final MutableLiveData<LocationList> locationListMutableLiveData = new MutableLiveData<>();
        apiService = ApiUtils.getAPIService();

        apiService.fetchAllUserLocation().enqueue(new Callback<List<AllUsersLocationResponse>>() {
            @Override
            public void onResponse(Call<List<AllUsersLocationResponse>> call, Response<List<AllUsersLocationResponse>> response) {
                if(response.isSuccessful()){
                    fetchAllUserLocationResponse.setValue(response.body());
                    Log.d("Response", "onResponse: ****Response Json**** " + response.body());

                    Log.d("Response", "onResponse: " + response.message());
                    Log.d("Response", "onResponse: " + response.code());
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("Response", "onResponse: " + jObjError.getString("message"));
                        fetchAllUserLocationResponse.setValue(response.body());
                    }catch (Exception e) {
                        Log.d("Response", "onResponse: " + e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<AllUsersLocationResponse>> call, Throwable t) {

            }
        });

        return fetchAllUserLocationResponse;
    }
}
