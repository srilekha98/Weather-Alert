package info.androidhive.navigationdrawer.utility;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import info.androidhive.navigationdrawer.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by mkukunooru on 5/22/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context ctx, Intent arg1) {
        // For our recurring task, we'll just display a message
        String message=app.getRes();
        // message = arg1.getExtras().getString("r");
        System.out.println("called"+message);
        Toast.makeText(ctx, "I'm running", Toast.LENGTH_SHORT).show();
        System.out.println("hey toks");
        NotificationManager nm = (NotificationManager)ctx.getSystemService(NOTIFICATION_SERVICE);
        //Create the notification here.
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("Weather alert...!")
                        .setContentText(message);
        // Sets an ID for the notification
        int mNotificationId = 001;
 

        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        nm.notify(mNotificationId, mBuilder.build());
    }

}
