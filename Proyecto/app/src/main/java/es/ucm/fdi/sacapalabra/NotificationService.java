package es.ucm.fdi.sacapalabra;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

    private static final int NOTIFICATION_ID = 10000;
    private static final long INTERVAL_MILLIS = 30 * 60 * 1000; // 30 minutes in milliseconds
    private final static String CHANNEL_ID = "channel_01";
    private boolean isRunning;
    private boolean isNotificationEnabled;

    private Handler handler;
    private Runnable notificationRunnable;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        handler = new Handler();
        notificationRunnable = new Runnable() {
            @Override
            public void run() {
                if (isNotificationEnabled) {
                    showNotification();
                }
                handler.postDelayed(this, INTERVAL_MILLIS);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        isNotificationEnabled = intent.getBooleanExtra("notification_enabled", false);
        handler.post(notificationRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        handler.removeCallbacks(notificationRunnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.sacapalabra_mascota)
                .setContentTitle("Pasapalabra")
                .setContentText("Ven a jugar!")
                .setPriority(NotificationCompat.PRIORITY_HIGH).setChannelId(CHANNEL_ID);

        Intent intent = new Intent(NotificationService.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}