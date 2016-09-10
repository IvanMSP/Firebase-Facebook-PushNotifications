package com.ivan.firebaseandroid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by neyma on 27/08/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "NOticias";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from =  remoteMessage.getFrom();
        Log.d(TAG,"Este wey lo mando:"+from);

        if(remoteMessage.getNotification() != null){
            Log.d(TAG,"Notificacion"+remoteMessage.getNotification().getBody());
            ShowNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
        if(remoteMessage.getData().size()>0){
            Log.d(TAG,"Data:"+ remoteMessage.getData());
        }
    }

    private void ShowNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Creamos el cuerpo de la notificacion

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.firebase)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }
}
