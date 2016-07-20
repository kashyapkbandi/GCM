package com.example.innovacx.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by kashyap on 20-07-2016.
 */



/*
*
THIS WILL BE USED TO REGISTER THE DEVICE AGAIN WHEN TOKEN OF THE SERVER     (IF)HAS BEEN CHANGED*
* */
public class GCMTokenRefreshListener extends InstanceIDListenerService {
    //If the token is changed registering the device again
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
