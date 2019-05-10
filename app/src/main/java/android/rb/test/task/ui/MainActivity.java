package android.rb.test.task.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.rb.test.task.R;
import android.rb.test.task.utils.SharedPrefrences;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding UI
        signInButton = findViewById(R.id.sign_in_button);



        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Setting Up Click Listeners
        signInButton.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Log.d("Response", "onStart: " + "*********Already Logged In*********");
            Log.d("Response","Account Name: " + account.getDisplayName());
            Log.d("Response","Account Email: " + account.getEmail());

            moveToDashboard(account.getEmail(),account.getDisplayName());

            //Storing Values in Shared Prefrences
            SharedPrefrences.setValues("email",account.getEmail(),this);
            SharedPrefrences.setValues("username",account.getDisplayName(),this);

        }
    }

    //SignIn Function(Peding intent is thrown)
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI
            Log.d("Response","Account Details Received " + account.getEmail());

            //Saving the Email we get from google
            SharedPrefrences.setValues("email",account.getEmail(),this);
            SharedPrefrences.setValues("username",account.getDisplayName(),this);

            //Sending To Main Dashboard After Successfull Login
            moveToDashboard(account.getEmail(),account.getDisplayName());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Response", "signInResult:failed code=" + e.getStatusCode());
            Log.d("Response","Error Fetching Data");
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }

    }

    //Move To Dashboard Screen if Login Sucessfull

    /**
     *
     * @param email
     * @param name
     */
    private void moveToDashboard(String email, String name){

        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        intent.putExtra("Email",email);
        intent.putExtra("Name",name);
        startActivity(intent);
    }
}
