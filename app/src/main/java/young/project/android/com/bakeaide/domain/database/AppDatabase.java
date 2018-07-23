package young.project.android.com.bakeaide.domain.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import young.project.android.com.bakeaide.domain.model.Recipe;

@Database(entities = {Recipe.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public abstract AppDao appDao();

    private static AppDatabase databaseInstance;

    public static AppDatabase getDatabaseInstance(final Context context){
        if(databaseInstance == null){
            synchronized (AppDatabase.class){
                if(databaseInstance == null){
                    databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return databaseInstance;
    }
}
