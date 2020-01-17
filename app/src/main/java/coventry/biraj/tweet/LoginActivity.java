package coventry.biraj.tweet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import coventry.biraj.twitter.R;
import coventry.biraj.tweet.bll.LoginBLL;
import coventry.biraj.tweet.registeractivities.RegisterActivity;
import coventry.biraj.tweet.strictmode.StrictModeClass;


public class LoginActivity extends AppCompatActivity {
    TextView register;
    EditText etusername, etpassword;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etusername = findViewById(R.id.etname);
        etpassword = findViewById(R.id.etPassword);
        register = findViewById(R.id.btnsignup);
        login = findViewById(R.id.btnlogin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();

                LoginBLL loginBLL = new LoginBLL();

                StrictModeClass.StrictMode();
                if (loginBLL.checkUser(username, password)) {
                    startActivity(new Intent(LoginActivity.this, DashActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    etusername.requestFocus();
                }
            }
        });
    }
}
