package android.rb.test.task.ui;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.rb.test.task.R;
import android.rb.test.task.service.models.AllUsersLocationResponse;
import android.rb.test.task.service.models.LocationList;
import android.rb.test.task.service.models.UploadUserLocationResponseModel;
import android.rb.test.task.utils.GPSTracker;
import android.rb.test.task.utils.SharedPrefrences;
import android.rb.test.task.viewmodel.MapViewModel;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Double latitude;
    private Double longitude;

    private static final int  REQUEST_ACCESS_FINE_LOCATION = 111;

    private MapViewModel mapViewModel;

    private boolean showAllUserLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        Intent intent = getIntent();
        showAllUserLocation = intent.getBooleanExtra("showAllUserLocation",false);


        //Checking For Current Location Permission
        boolean hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionLocation) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Getting Current Location From the GPSTracker Service
        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if(showAllUserLocation){
            //Calling All Apis'
            fetchAllUserLocationRequest(mapViewModel.fetchAllUserLocationResponse());
        }

    }



    /**
     * On Map Ready Show User's Current Location
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // For showing a move to my location button
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            googleMap.setMyLocationEnabled(true);
        }
        // Add a marker in Sydney and move the camera
        LatLng currentLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        String username = SharedPrefrences.getValues("username",this);
        String email    = SharedPrefrences.getValues("email",this);

        uploadUserLocationRequest(mapViewModel.uploadUserLocationRequest(username,email,latitude,longitude));
    }



    //Caliing the uploadUserLocation Api
    private void uploadUserLocationRequest(LiveData<UploadUserLocationResponseModel> uploadUserLocationResponseModelLiveData){
        uploadUserLocationResponseModelLiveData.observe(this, new Observer<UploadUserLocationResponseModel>() {
            @Override
            public void onChanged(UploadUserLocationResponseModel uploadUserLocationResponseModel) {
                Log.d("Response", "onChanged: ***** ID ****** " + uploadUserLocationResponseModel.getId());
            }
        });
    }

    private void fetchAllUserLocationRequest(LiveData<List<AllUsersLocationResponse>> allUsersLocationResponseLiveData){
        allUsersLocationResponseLiveData.observe(this, new Observer<List<AllUsersLocationResponse>>() {
            @Override
            public void onChanged(List<AllUsersLocationResponse> allUsersLocationResponses) {
                Log.d("Response", "onChanged: *** UserName *** " + allUsersLocationResponses.get(0).getUsername());
                Log.d("Response", "onChanged: *** UserName *** " + allUsersLocationResponses.get(1).getUsername());
                Log.d("Response", "onChanged: *** UserName *** " + allUsersLocationResponses.get(2).getUsername());

                //Place the markers on the map. When we get all userLocations..
                for (int i = 0; i < allUsersLocationResponses.size(); i++){
                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(allUsersLocationResponses.get(i).getLat(), allUsersLocationResponses.get(i).getLng()))
                            .title(allUsersLocationResponses.get(i).getUsername());
                    mMap.addMarker(marker);
                }

            }

        });
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {

            case REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("Response", "onRequestPermissionsResult: " + "Permission Granted");


                } else
                {
                    Toast.makeText(this, "The app was not allowed to get your location. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }

        }

    }


}
