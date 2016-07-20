package com.example.innovacx.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by Kashyap on 20-07-2016.
 */
public class GCMRegistrationIntentService extends IntentService {

    //Constants for success and errors
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    //Default constructor
    public GCMRegistrationIntentService() {
        super("");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
//       REGISTER THE GCM TO THE DEVICE
        registerGCM();

    }

    private void registerGCM() {//Registration complete intent initially null
        Intent registrationComplete = null;

        //Register token is also null
        //we will get the token on successfull registration
        String token = null;
        try {
            //Creating INSTANCE ID
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());

            //GETTING THE TOKEN FROM INSTANCE ID

            // DEFAULT SENDER ID BY AZM
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            //Displaying the token in the log so that we can copy it to send push notification
            Log.w("GCMRegIntentService", "token:" + token);
            // CAN COPY THIS TOKEN TO SERVER , DO FURTHER EXTENSIONS


            //CREATE INTENT WITH SUCCESS ON REGISTRATION COMPLETE
            registrationComplete = new Intent(REGISTRATION_SUCCESS);

            //PUTTING THE TOKEN IN INTENT
            registrationComplete.putExtra("token", token);
        } catch (Exception e) {
            //If any error occurred
            Log.w("GCMRegIntentService", "Registration error");
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }

        //Sending the broadcast that registration is completed
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
