package android.rb.test.task.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.rb.test.task.R;
import android.rb.test.task.service.models.UploadUserLocationResponseModel;
import android.rb.test.task.utils.SharedPrefrences;
import android.rb.test.task.viewmodel.MapViewModel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView name_textview;
    private TextView email_textview;
    private TextView db_email_textview;
    private Button   current_location_button;
    private Button   show_all_users_button;

    private String  user_email;
    private String  user_name;

    private MapViewModel mapViewModel;

    private boolean locationPermissionEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        //Getting Values From Intent
        Intent values = getIntent();
        user_email         =  values.getStringExtra("Email");
        user_name          = values.getStringExtra("Name");



        //Binding View
        name_textview               = findViewById(R.id.name_textview);
        email_textview              = findViewById(R.id.email_textview);
        current_location_button     = findViewById(R.id.current_location_button);
        show_all_users_button       = findViewById(R.id.show_all_users_button);
        db_email_textview           = findViewById(R.id.db_email_textview);


        current_location_button.setOnClickListener(this);
        show_all_users_button.setOnClickListener(this);

        //populating UI After Getting the values
        populateUI();

        //checking for location Permission
        checkLocationPermission();

    }

    private void populateUI(){

        //Poupulating UI
        name_textview.setText(user_name);
        email_textview.setText(user_email);

        //Setting up Email saved in DB
        db_email_textview.setText(SharedPrefrences.getValues("email",this));

    }



    //Checking For Location Permission
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this,"Location Permission Denied.Please Enable Location Permission",Toast.LENGTH_LONG);
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //Checking Location Permission
    private void checkLocationPermission(){

        //checking for location Permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            // Write you code here if permission already given.
            locationPermissionEnabled = true;
        }
    }

    @Override
    public void onClick(View v) {

        if(v.equals(current_location_button)){
            if (locationPermissionEnabled) {
                //OnCLick Events for current location button to be called here
                Intent intent = new Intent(DashboardActivity.this, MapActivity.class);
                startActivity(intent);
            }else {


            }

        }

        else if(v.equals(show_all_users_button)){
            //On Click for show all user Buttons

            if (locationPermissionEnabled){
                Intent intent = new Intent(DashboardActivity.this, MapActivity.class);
                intent.putExtra("showAllUserLocation",true);
                startActivity(intent);
            }else {
                //Requesting Permission Again
                Toast.makeText(this,"Enable Location Permission",Toast.LENGTH_LONG);
                //checking for location Permission
                checkLocationPermission();
            }



        }

    }
}
