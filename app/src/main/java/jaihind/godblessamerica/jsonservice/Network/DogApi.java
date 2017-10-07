package jaihind.godblessamerica.jsonservice.Network;

import jaihind.godblessamerica.jsonservice.DogImagePojo;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nande on 10/7/2017.
 */

public interface DogApi {
    
    @GET("api/breeds/image/random")
    Call<DogImagePojo> getDogPicture();
}
