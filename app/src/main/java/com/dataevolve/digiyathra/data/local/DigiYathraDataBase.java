package com.dataevolve.digiyathra.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dataevolve.digiyathra.data.local.dao.LoginDao;
import com.dataevolve.digiyathra.data.local.entry.LoginEntity;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {LoginEntity.class}, version = 1)
public abstract class DigiYathraDataBase extends RoomDatabase {
    public abstract LoginDao loginDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile DigiYathraDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DigiYathraDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DigiYathraDataBase.class) {
                if (INSTANCE == null) {

                    final byte[] passphrase = SQLiteDatabase.getBytes(("test123").toCharArray());

                    final SupportFactory factory = new SupportFactory(passphrase);


                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DigiYathraDataBase.class, "digiyathra_database")
                            .openHelperFactory(factory)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                // LoginDao dao = INSTANCE.loginDao();
                // dao.deleteAll();

                // Login login = new Login("Hello");
                //  dao.insert(login);
                //  login = new Login("ttt");
                // dao.insert(login);

            });
        }
    };

}