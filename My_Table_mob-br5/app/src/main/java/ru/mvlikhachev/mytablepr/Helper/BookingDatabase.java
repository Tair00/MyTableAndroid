package ru.mvlikhachev.mytablepr.Helper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Interface.CartDao;

@Database(entities = {RestoranDomain.class}, version = 1)
public abstract class BookingDatabase extends RoomDatabase {
    private static BookingDatabase instance;

    public abstract CartDao cartDao();

    public static synchronized BookingDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BookingDatabase.class, "booking_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}