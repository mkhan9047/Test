package cycleshop.com.testofkhan.Networking;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {

    @Multipart
    @POST("/employee/script/")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);

}
