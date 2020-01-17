package coventry.biraj.tweet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import coventry.biraj.tweet.adapter.PostAdapter;
import coventry.biraj.tweet.api.PostAPI;
import coventry.biraj.tweet.api.UsersAPI;
import coventry.biraj.tweet.url.Url;
import coventry.biraj.twitter.R;
import coventry.biraj.tweet.model.Posts;
import coventry.biraj.tweet.model.User;
import coventry.biraj.tweet.model.Posts;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashActivity extends AppCompatActivity {
    ImageView addTweet;
    CircleImageView imgProfile;
    RecyclerView recyclerView;
    List<Posts> postsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        recyclerView = findViewById(R.id.recyclerView);
        addTweet = findViewById(R.id.btnadd);
        imgProfile = findViewById(R.id.imgProfile);

//
//        Posts posts = new Posts("Aashish Shrestha", "@aashish", "3m", "By signing up, you agree to the Terms of Service and Privacy Policy,including Cookies use. others will be able to find you by email or phone number when provided.","","","");
//        postsList = posts.getPostsList();
//
//        if(postsList.isEmpty()) {
//            postsList.add(new Posts("Aashish Shrestha", "@aashish", ".3m", "If you can imagine a furry humanoid seven feet tall, with the face of an intelligent gorilla and the braincase of a man, you'll have a rough idea of what they looked like -- except for their teeth. The canines would have fitted better in the face of a tiger, and showed at the corners of their wide, thin-lipped mouths, giving them an expression of ferocity.","","",""));
//            postsList.add(new Posts("James Brown", "@brwjames", ".6m", "By signing up, you agree to the Terms of Service and Privacy Policy,including Cookies use. others will be able to find you by email or phone number when provided.","398","64","97"));
//            postsList.add(new Posts("Aashish Shrestha", "@aashish", ".3m", "If you can imagine a furry humanoid seven feet tall, with the face of an intelligent gorilla and the braincase of a man, you'll have a rough idea of what they looked like -- except for their teeth. The canines would have fitted better in the face of a tiger, and showed at the corners of their wide, thin-lipped mouths, giving them an expression of ferocity.","","",""));
//            postsList.add(new Posts("James Brown", "@brwjames", ".6m", "By signing up, you agree to the Terms of Service and Privacy Policy,including Cookies use. others will be able to find you by email or phone number when provided.","","",""));
//            postsList.add(new Posts("Aashish Shrestha", "@aashish", ".3m", "If you can imagine a furry humanoid seven feet tall, with the face of an intelligent gorilla and the braincase of a man, you'll have a rough idea of what they looked like -- except for their teeth. The canines would have fitted better in the face of a tiger, and showed at the corners of their wide, thin-lipped mouths, giving them an expression of ferocity.","","",""));
//            postsList.add(new Posts("James Brown", "@brwjames", ".6m", "By signing up, you agree to the Terms of Service and Privacy Policy,including Cookies use. others will be able to find you by email or phone number when provided.","","",""));
//            postsList.add(new Posts("Aashish Shrestha", "@aashish", ".3m", "If you can imagine a furry humanoid seven feet tall, with the face of an intelligent gorilla and the braincase of a man, you'll have a rough idea of what they looked like -- except for their teeth. The canines would have fitted better in the face of a tiger, and showed at the corners of their wide, thin-lipped mouths, giving them an expression of ferocity.","","",""));
//            postsList.add(new Posts("James Brown", "@brwjames", ".6m", "By signing up, you agree to the Terms of Service and Privacy Policy,including Cookies use. others will be able to find you by email or phone number when provided.","","",""));
//            posts.setPostsList(postsList);
//
//            PostAdapter postAdapter = new PostAdapter(this, postsList);
//            recyclerView.setAdapter(postAdapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        }

        PostAPI postAPI = Url.getInstance().create(PostAPI.class);

        Call<List<Posts>> listCall = postAPI.getPosts();


        listCall.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (!response.isSuccessful()) {
//                    display.setText("Code :" + response.code());
//                    return;
                    Toast.makeText(DashActivity.this, "error" + response.code(), Toast.LENGTH_LONG).show();
                    Log.d("error", "error" + response.code());
                    return;
                }
                List<Posts> postsList = response.body();



                PostAdapter postAdapter = new PostAdapter(DashActivity.this, postsList);
                recyclerView.setAdapter(postAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {

            }
        });


        addTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashActivity.this, AddTweetActivity.class));
            }
        });
        loadCurrentUser();
    }

    private void loadCurrentUser() {
        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<User> userCall = usersAPI.getUserDetails(Url.token);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DashActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                String imgPath = Url.imagePath + response.body().getImage();

                Picasso.get().load(imgPath).into(imgProfile);


//                StrictModeClass.StrictMode();
//                try {
//                    URL url = new URL(imgPath);
//                    imgProgileImg.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(DashActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
