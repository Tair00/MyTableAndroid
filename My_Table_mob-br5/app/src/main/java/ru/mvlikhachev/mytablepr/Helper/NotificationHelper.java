package ru.mvlikhachev.mytablepr.Helper;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import ru.mvlikhachev.mytablepr.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "YourChannelId";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "YourChannelName"; // Название вашего канала
            String description = "YourChannelDescription"; // Описание вашего канала
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void sendNotification(Context context, String title, String message) {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.round_table) // Замените на вашу иконку
                .setContentTitle(title)
                .setContentText(message)
                .setChannelId(CHANNEL_ID);

        notificationManager.notify(1, builder.build());
    }
}
