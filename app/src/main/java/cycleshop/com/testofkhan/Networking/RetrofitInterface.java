package cycleshop.com.testofkhan.Networking;

import cycleshop.com.testofkhan.UploadModel;
import okhttp3.MultipartBody;
        import okhttp3.RequestBody;

        import retrofit2.Call;
        import retrofit2.http.Multipart;
        import retrofit2.http.POST;
        import retrofit2.http.Part;

public interface RetrofitInterface {


    @Multipart
    @POST("/employee/script/save_employee.php")
    Call<UploadModel> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("age") RequestBody age, @Part("image_name") RequestBody imageName);

}
