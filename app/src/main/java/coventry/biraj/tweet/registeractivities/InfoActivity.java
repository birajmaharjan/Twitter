package coventry.biraj.tweet.registeractivities;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import coventry.biraj.twitter.R;
import coventry.biraj.tweet.LoginActivity;
import coventry.biraj.tweet.api.UsersAPI;
import coventry.biraj.tweet.server_response.ImageResponse;
import coventry.biraj.tweet.strictmode.StrictModeClass;
import coventry.biraj.tweet.url.Url;
import coventry.biraj.twitter.R;
import coventry.biraj.tweet.model.User;
import coventry.biraj.tweet.server_response.SignUpResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {
    ImageView imgProfile;
    TextView etname;
    private EditText etpassword, etbio;
    String imagePath, username;
    String imageName = "";
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);




        imgProfile = findViewById(R.id.imgProfile);
        etname = findViewById(R.id.etname);
        etbio = findViewById(R.id.etBio);
        etpassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnsignup);

        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        etname.setText(username);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(validate()) {
                        saveImageOnly();
                        signUp();
                    }
            }
        });

    }

    private boolean validate() {
        boolean status=true;
        if (etname.getText().toString().length() < 6) {
            etname.setError("Minimum 6 character");
            status=false;
        }
        return status;
    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imgProfile.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
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

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);

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
    private void signUp() {


        String name = username;
        String bio = etbio.getText().toString();
        String password = etpassword.getText().toString();

        User users = new User(name, password, bio, imageName);

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<SignUpResponse> signUpCall = usersAPI.registerUser(users);

        signUpCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(InfoActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(InfoActivity.this, "Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(InfoActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(InfoActivity.this, LoginActivity.class));
    }
}
