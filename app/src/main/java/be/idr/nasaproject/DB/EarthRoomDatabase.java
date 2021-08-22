package be.idr.nasaproject.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {EarthData.class}, version = 1, exportSchema = false)
public abstract class EarthRoomDatabase extends RoomDatabase {

    abstract EarthDataDao earthDateDao();

    private static volatile EarthRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static EarthRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EarthRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EarthRoomDatabase.class, "nasa_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                EarthDataDao dao = INSTANCE.earthDateDao();
                dao.deleteAll();

//                EarthDate earthDate = new EarthDate("Hello");
//                dao.insert(earthDate);
//                earthDate = new EarthDate("test");
//                dao.insert(earthDate);
            });
        }
    };
}
