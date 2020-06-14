package mhandharbeni.eparking.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;

import mhandharbeni.eparking.database.helpers.DataConverter;
import mhandharbeni.eparking.database.helpers.DateConverter;
import mhandharbeni.eparking.database.interfaces.InterfaceKendaraan;
import mhandharbeni.eparking.database.models.response.Kendaraan;
import mhandharbeni.eparking.utilitas.Constant;


@TypeConverters({DateConverter.class, DataConverter.class})
@Database(
        entities = {
                Kendaraan.class
        },
        version = Constant.versionDb,
        exportSchema = Constant.exportSchema
)
public abstract class AppDB extends RoomDatabase {
    public abstract InterfaceKendaraan kendaraan();

    private static volatile AppDB INSTANCE;
    private static Migration[] migrations = new Migration[]{};
    public static AppDB getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (AppDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDB.class,
                            Constant.nameDb
                    )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
