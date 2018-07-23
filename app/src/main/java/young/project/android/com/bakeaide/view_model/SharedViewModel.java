package young.project.android.com.bakeaide.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import young.project.android.com.bakeaide.domain.Repository;
import young.project.android.com.bakeaide.domain.model.Recipe;

public class SharedViewModel extends AndroidViewModel {

    private LiveData<List<Recipe>> mRecipeListLiveData;
    final private Repository mRepository;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getRepositoryInstance(application);
    }

    public LiveData<List<Recipe>> getRecipeListLiveData() {
        if(mRecipeListLiveData == null){
            mRecipeListLiveData = new MutableLiveData<>();
            mRecipeListLiveData = mRepository.getRecipeList();
            return mRecipeListLiveData;
        } else{
            return mRecipeListLiveData;
        }

    }

}
