package ru.mvlikhachev.mytablepr.Helper;

//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.room.migration.Migration;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//
//import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
//import ru.mvlikhachev.mytablepr.Interface.CartDao;
//
//
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//
//import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
//import ru.mvlikhachev.mytablepr.Interface.CartDao;
//@Database(entities = {RestoranDomain.class}, version = 3)
//public abstract class CartDatabase extends RoomDatabase {
//
//    private static CartDatabase instance;
//
//    public abstract CartDao cartDao();
//
//    public static synchronized CartDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                            CartDatabase.class, "cart_database")
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
//        return instance;
//    }
//    // Миграция от версии 1 до версии 3
////    static final Migration MIGRATION_1_3 = new Migration(1, 3) {
////        @Override
////        public void migrate(SupportSQLiteDatabase database) {
////            // Создание новой таблицы
////            database.execSQL("CREATE TABLE IF NOT EXISTS `new_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT)");
////
////            // Копирование данных из старой таблицы в новую таблицу
////            database.execSQL("INSERT INTO `new_table` (`id`, `name`) SELECT `id`, `name` FROM `cart_items`");
////
////            // Удаление старой таблицы
////            database.execSQL("DROP TABLE IF EXISTS `cart_items`");
////
////            // Переименование новой таблицы в старое имя
////            database.execSQL("ALTER TABLE `new_table` RENAME TO `cart_items`");
////        }
////    };
//
//}
