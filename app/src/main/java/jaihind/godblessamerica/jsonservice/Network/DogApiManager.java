package jaihind.godblessamerica.jsonservice.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nande on 10/7/2017.
 */

public class DogApiManager {
    
    private static final String baseUrl = "https://dog.ceo/";
    
    private DogApi dogApi;
    
    public DogApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dogApi = retrofit.create(DogApi.class);
    }
    
    public DogApi getDogApi() {
        return dogApi;
    }
}
