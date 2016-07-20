package com.example.innovacx.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Kashyap on 20-07-2016.
 */


/*
*
* THIS IS THE RECEIVER FOR THE ANDROID PUSH NOTIFICATION USING GCM
*
* */
public class GCMPushReceiverService extends GcmListenerService {
    /*
    * THIS METHOD WILL BE CALLED WHEN EVER A NEW MESSAGE ARRIVES
    * */
    @Override
    public void onMessageReceived(String from, Bundle data) {

        //PULLING OUT MESSAGE FROM THE BUNDLE AND SETTING TO DATA
        String message = data.getString("message");

        //DISPLAY THE NOTIFICATION WITH THE MESSAGE USING A METHOD
        // IMPLEMENT THE sendNotification( )
        sendNotification(message);
    }

    private void sendNotification(String message) {
        /*
        * IMPLEMENTATION OF THE METHOD , USED TO DISPLAY NOTIFICATION WITH THE MESSAGE
        * */


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);


        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.alertsmall)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }
}
