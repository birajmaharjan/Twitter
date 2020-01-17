package coventry.biraj.tweet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import coventry.biraj.tweet.registeractivities.RegisterActivity;
import coventry.biraj.twitter.R;

public class CreateAccountActivity extends AppCompatActivity {

    Button signup;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        signup = findViewById(R.id.btnsignup);
        login = findViewById(R.id.tvlogin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
            }
        });

    }
}
