package cycleshop.com.testofkhan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText personName, PersonAge, personEmail;
    Bitmap bitmap;
    ImageView personImage;


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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            //pick image from gallery
            Cursor cursor = null;

            Uri selectedImage = data.getData();

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
