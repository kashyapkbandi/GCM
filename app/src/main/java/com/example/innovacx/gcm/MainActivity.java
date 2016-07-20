package com.example.innovacx.gcm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends Activity {

/*
* CREATE A BROADCAST RECEIVER FOR GCM REGISTRATION
* */
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initializing our broadcast receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            //When the broadcast received
            //We are sending the broadcast from GCMRegistrationIntentService

            public void onReceive(Context context, Intent intent) {


                if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    // WHEN BROADCAST IS RECEIVED SUCCESSFULLY THAT MEANS DEVICE IS REGISTERED SUCCESSFULLY

                    //GET THE REGISTRATION TOKEN FROM THE INTENT
                    String token = intent.getStringExtra("token");
                    // NOW , WE GOT THE REGISTRATION TOKEN FROM THE INTENT

                /*
                * DISPLAYING TOKEN AS A TOAST MESSAGE*/
                //Displaying the token as toast
                Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();

                // /if the intent is not with success then displaying error messages
            } else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
            }

            }
        };

        //Checking play service is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        //if play service is not available
        if(ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                //If play service is not supported
                //Displaying an error message
            } else {
                Toast.makeText(getApplicationContext(), "This device does not supports  Google Play Service!", Toast.LENGTH_LONG).show();
            }

            //If play service is available
        } else {
            //Starting intent to register device
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }
    }


    //Registering receiver on activity resume
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

}
