package coventry.biraj.tweet.registeractivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import coventry.biraj.tweet.LoginActivity;
import coventry.biraj.twitter.R;

public class RegisterActivity extends AppCompatActivity {
    ImageView btnBack;
    EditText etname;
    Button Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = findViewById(R.id.back);
        etname = findViewById(R.id.name);
        Signup = findViewById(R.id.btnsignup);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etname.getText().toString();
                Intent intent = new Intent(RegisterActivity.this, InfoActivity.class);
                intent.putExtra("name",username);
                startActivity(intent);
//                startActivity(new Intent(RegisterActivity.this, InfoActivity.class));
            }
        });

    }
}
