package young.project.android.com.bakeaide.domain.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import young.project.android.com.bakeaide.domain.model.Recipe;

@Dao
public interface AppDao {

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAll();

    @Query("SELECT * FROM recipe")
    List<Recipe> getAllNoLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Recipe> recipes);
}
