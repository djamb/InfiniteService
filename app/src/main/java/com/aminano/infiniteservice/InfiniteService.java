package com.aminano.infiniteservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;

public class InfiniteService extends Service {

  private PowerManager.WakeLock mWakeLock;
  private final static String channelId = "aminano";

  @Override
  public void onCreate() {
    super.onCreate();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      startMyOwnForeground();
    } else {
      startForeground(211, new Notification());
    }
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
    mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "PartialWakeLockTag:");
    mWakeLock.acquire();
    return START_STICKY;
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  private void startMyOwnForeground() {
    NotificationManager manager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    NotificationChannel channel =
        new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_NONE);
    manager.createNotificationChannel(channel);
    Notification notification = new NotificationCompat.Builder(this, channelId).build();
    startForeground(220, notification);
  }
}