package young.project.android.com.bakeaide.domain.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import young.project.android.com.bakeaide.domain.model.Recipe;

public interface NetworkApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getAllRecipes();

}
