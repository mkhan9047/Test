package cycleshop.com.testofkhan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.lang.UProperty;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import cycleshop.com.testofkhan.Networking.RetrofitBuilder;
import cycleshop.com.testofkhan.Networking.RetrofitInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class MainActivity extends AppCompatActivity {

    EditText personName, PersonAge, personEmail;
    Bitmap bitmap;
    ImageView personImage;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitVIew();
    }


    public void InitVIew() {
        personEmail = findViewById(R.id.personEmail);
        PersonAge = findViewById(R.id.personAge);
        personName = findViewById(R.id.personName);
        personImage = findViewById(R.id.personImage);
    }

    public void onChoose(View view) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, 2);

    }

    public void onSave(View view) {

        UploadFile(selectedImage);

    }


    private void UploadFile(Uri fileUri) {

        RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofit().create(RetrofitInterface.class);

        /*Multipart*/

        RequestBody name = RequestBody.create(MultipartBody.FORM, personName.getText().toString());
        RequestBody email = RequestBody.create(MultipartBody.FORM, personEmail.getText().toString());
        RequestBody aget = RequestBody.create(MultipartBody.FORM, PersonAge.getText().toString());
        RequestBody imageName = RequestBody.create(MultipartBody.FORM, "firstimage");

        File file = new File(getPath(fileUri));

        RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);

        MultipartBody.Part multipart = MultipartBody.Part.createFormData("file", file.getName(), filePart);

        final Call<UploadModel> requestBodyCall = retrofitInterface.postImage(multipart, name, email, aget, imageName);

        requestBodyCall.enqueue(new Callback<UploadModel>() {
            @Override
            public void onResponse(Call<UploadModel> call, Response<UploadModel> response) {

                UploadModel model = response.body();

                if (model != null) {

                    Log.d("response From Server", model.toString());

                }

            }

            @Override
            public void onFailure(Call<UploadModel> call, Throwable t) {
                Log.d("error from server", t.getMessage());
            }
        });

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            //pick image from gallery

            Cursor cursor = null;

            selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            if (selectedImage != null) {

                cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            }
            // Move to first row
            if (cursor != null) {

                cursor.moveToFirst();
            }

            int columnIndex = 0;

            if (cursor != null) {

                columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            }

            String imgDecodableString = null;

            if (cursor != null) {

                imgDecodableString = cursor.getString(columnIndex);
            }

            assert cursor != null;

            cursor.close();

            bitmap = BitmapFactory.decodeFile(imgDecodableString);

            personImage.setImageBitmap(bitmap);
        }
    }

}
