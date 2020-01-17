package coventry.biraj.tweet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import coventry.biraj.twitter.R;
import coventry.biraj.tweet.api.PostAPI;
import coventry.biraj.tweet.model.Posts;
import coventry.biraj.tweet.server_response.ImageResponse;
import coventry.biraj.tweet.strictmode.StrictModeClass;
import coventry.biraj.tweet.url.Url;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTweetActivity extends AppCompatActivity {
    CircleImageView imgProfile, imgProfile2;
    private EditText etusername, etname, etstory, etlike, etcomment, etretweet, ettime;
    String imagePath, imagePath2;
    String imageName = "";
    String imageName2 = "";
    Button btnTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tweet);

        imgProfile = findViewById(R.id.imgProfile);
//        imgProfile2 = findViewById(R.id.post);
        etusername = findViewById(R.id.username);
        etname = findViewById(R.id.name);
        etstory = findViewById(R.id.story);
        etlike = findViewById(R.id.like);
        etcomment = findViewById(R.id.comment);
        etretweet = findViewById(R.id.tweet);
        ettime = findViewById(R.id.time);
        btnTweet = findViewById(R.id.btnTweet);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
//        imgProfile2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BrowseImage2();
//            }
//        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnly();
//                saveImageOnly2();
                Tweet();
            }
        });

    }


    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

//    private void BrowseImage2() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, 02);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
            Uri uri = data.getData();
            imgProfile.setImageURI(uri);
            imagePath = getRealPathFromUri(uri);
        }
//        if (resultCode == 02) {
//
//            Uri uri = data.getData();
//            imgProfile2.setImageURI(uri);
//            imagePath2 = getRealPathFromUri(uri);
//        }

    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }


    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        PostAPI postAPI = Url.getInstance().create(PostAPI.class);
        Call<ImageResponse> responseBodyCall = postAPI.uploadImage(body);

        StrictModeClass.StrictMode();
        //Synchronous methid
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

//    private void saveImageOnly2() {
//        File file = new File(imagePath2);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
//                file.getName(), requestBody);
//
//        PostAPI postAPI = Url.getInstance().create(PostAPI.class);
//        Call<ImageResponse> responseBodyCall = postAPI.uploadImage(body);
//
//        StrictModeClass.StrictMode();
//        //Synchronous methid
//        try {
//            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
//            imageName = imageResponseResponse.body().getFilename();
//            Toast.makeText(this, "Image inserted" + imageName2, Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }

    private void Tweet() {


        String username = etusername.getText().toString();
        String name = etname.getText().toString();
        String story = etstory.getText().toString();
        String like = etlike.getText().toString();
        String comment = etcomment.getText().toString();
        String retweet = etretweet.getText().toString();
        String time = ettime.getText().toString();

        Posts posts = new Posts(username, name, time, story, like, comment, retweet, imageName, imageName2);

        PostAPI postAPI = Url.getInstance().create(PostAPI.class);
        Call<Void> voidCall = postAPI.addPosts(posts);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddTweetActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AddTweetActivity.this, "Added: ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddTweetActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
