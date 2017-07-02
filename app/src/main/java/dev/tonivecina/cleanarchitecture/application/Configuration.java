package dev.tonivecina.cleanarchitecture.configuration;

import android.app.Application;
import android.arch.persistence.room.Room;

import dev.tonivecina.cleanarchitecture.DLog;

/**
 * @author Toni Vecina on 6/7/17.
 */

public final class Configuration extends Application {
    private static final String DATABASE_NAME = "notesDB";

    private static Configuration instance = new Configuration();
    private AppDataBase appDataBase;

    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (this) {
            appDataBase = Room
                    .databaseBuilder(this, AppDataBase.class, DATABASE_NAME)
                    .build();

            DLog.info("Database initialized.");
        }

        DLog.success("Application is ready!");
    }

    //region GettersReg
    public static synchronized Configuration getInstance() {
        return instance;
    }

    public AppDataBase getAppDataBase() {
        return appDataBase;
    }
    //endregion
}
