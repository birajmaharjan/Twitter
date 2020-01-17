package coventry.biraj.tweet.api;

import java.util.List;

import coventry.biraj.tweet.model.Posts;
import coventry.biraj.tweet.server_response.ImageResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostAPI {
    @GET("posts")
    Call<List<Posts>> getPosts();

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @POST("posts/add")
    Call<Void> addPosts(@Body Posts posts);

}
