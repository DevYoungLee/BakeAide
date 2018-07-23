package young.project.android.com.bakeaide.domain.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final String RECIPE_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static NetworkApi networkApiInstance;

    public static NetworkApi getNetworkApiInstance(){
        if(networkApiInstance == null){
            synchronized (NetworkClient.class) {
                if(networkApiInstance == null){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(RECIPE_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    networkApiInstance = retrofit.create(NetworkApi.class);
                }
            }

        }
        return networkApiInstance;
    }

}
