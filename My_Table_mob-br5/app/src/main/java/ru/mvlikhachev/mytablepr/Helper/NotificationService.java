package ru.mvlikhachev.mytablepr.Helper;
import static android.app.Service.START_STICKY;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;


import androidx.core.app.NotificationCompat;

import ru.mvlikhachev.mytablepr.R;

public class NotificationService extends IntentService  {
    private boolean isServiceRunning = false;
    public NotificationService() {
        super("NotificationService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        // Здесь вы можете выполнять проверку данных с сервера и отправку уведомлений
        // Периодически вызывайте этот метод из службы для мониторинга данных
        // и отправки уведомлений
    }
    @Override
    public void onCreate() {
        super.onCreate();
        isServiceRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isServiceRunning) {
            // Выполните проверку данных с сервера
            // Если есть новые элементы с статусом "approved", отправьте уведомление

            // Отправка уведомления
            sendNotification("Новые элементы с статусом 'approved'");
        }

        // Возврат START_STICKY, чтобы служба автоматически перезапускалась при завершении
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceRunning = false;
    }

    private void sendNotification(String message) {
        // Отправка уведомления аналогично предыдущим примерам
    }
}
