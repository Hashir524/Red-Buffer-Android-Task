package android.rb.test.task.service.remote;

import android.rb.test.task.service.models.AllUsersLocationResponse;
import android.rb.test.task.service.models.LocationList;
import android.rb.test.task.service.models.UploadUserLocationResponseModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {


    @POST("/location")
    Call<UploadUserLocationResponseModel> uploadUserLocation(@Body RequestBody body);

    @GET("/location")
    Call<List<AllUsersLocationResponse>> fetchAllUserLocation();




}