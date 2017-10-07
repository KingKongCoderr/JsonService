package jaihind.godblessamerica.jsonservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nande on 10/7/2017.
 */

public class DogImagePojo {
    
    /*@SerializedName("status")
    @Expose*/
    private String status;
   /* @SerializedName("message")
    @Expose*/
    private String message;
    
    public DogImagePojo() {
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
}
