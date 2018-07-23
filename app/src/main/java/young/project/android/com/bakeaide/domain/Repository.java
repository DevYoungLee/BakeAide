package young.project.android.com.bakeaide.domain;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import young.project.android.com.bakeaide.domain.database.AppDao;
import young.project.android.com.bakeaide.domain.database.AppDatabase;
import young.project.android.com.bakeaide.domain.model.Recipe;
import young.project.android.com.bakeaide.domain.network.NetworkApi;
import young.project.android.com.bakeaide.domain.network.NetworkClient;

public class Repository {
    private static Repository repositoryInstance;
    final private NetworkApi networkApi;
    private AppDao mAppDao;

    private Repository(Context context){
        networkApi = NetworkClient.getNetworkApiInstance();
        mAppDao = AppDatabase.getDatabaseInstance(context).appDao();
    }

    public static Repository getRepositoryInstance(Context context) {
        if(repositoryInstance == null){
            synchronized (Repository.class){
                if(repositoryInstance == null){
                    repositoryInstance = new Repository(context);
                }
            }
        }
        return repositoryInstance;
    }

    public LiveData<List<Recipe>> getRecipeList(){
        getRecipeListFromNetwork();
        return mAppDao.getAll();
    }

    public void getRecipeListForWidget(RepositoryCallbacks<List<Recipe>> repositoryCallbacks){
        new getRecipeListAsyncTask(mAppDao, repositoryCallbacks).execute();
    }

    private void getRecipeListFromNetwork(){
        Call<List<Recipe>> call = networkApi.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                new insertRecipeListAsyncTask(mAppDao, response.body()).execute();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private static class insertRecipeListAsyncTask extends AsyncTask<Void, Void, Void>{

        private AppDao mAsyncAppDao;
        private List<Recipe> mRecipeList;

        insertRecipeListAsyncTask(AppDao appdao, List<Recipe> recipeList){
            mAsyncAppDao = appdao;
            mRecipeList = recipeList;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncAppDao.insertAll(mRecipeList);
            return null;
        }
    }

    private static class getRecipeListAsyncTask extends AsyncTask<Void, Void, List<Recipe>>{

        private AppDao mAsyncAppDao;
        private RepositoryCallbacks<List<Recipe>> mRepositoryCallbacks;


        getRecipeListAsyncTask(AppDao appdao, RepositoryCallbacks<List<Recipe>> repositoryCallbacks){
            mAsyncAppDao = appdao;
            mRepositoryCallbacks = repositoryCallbacks;

        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            return mAsyncAppDao.getAllNoLiveData();
        }

        @Override
        protected void onPostExecute(List<Recipe> recipeList) {
            mRepositoryCallbacks.onSuccess(recipeList);
            super.onPostExecute(recipeList);
        }
    }

}
