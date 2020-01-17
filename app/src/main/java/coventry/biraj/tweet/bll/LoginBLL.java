package coventry.biraj.tweet.bll;

import coventry.biraj.tweet.api.UsersAPI;
import coventry.biraj.tweet.server_response.SignUpResponse;
import coventry.biraj.tweet.url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBLL {

    boolean isSuccess = false;

    public boolean checkUser(String username, String password) {

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<SignUpResponse> usersCall = usersAPI.checkUser(username, password);

        try {
            Response<SignUpResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("Login success!")) {

                Url.token += loginResponse.body().getToken();
                // Url.Cookie = imageResponseResponse.headers().get("Set-Cookie");
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
