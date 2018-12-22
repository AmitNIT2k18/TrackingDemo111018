package com.example.amitbaranwal.trackingdemo11_10_18;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private FirebaseAuth mAuth;
    private ProgressDialog loaddingBar;

    private Button LoginButton;
    private EditText LoginEmail;
    private EditText LoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Sign in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoginButton = (Button)findViewById(R.id.login_button);
        LoginEmail = (EditText) findViewById(R.id.login_email);
        LoginPassword = (EditText) findViewById(R.id.login_password);

        loaddingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = LoginEmail.getText().toString();
                String password = LoginPassword.getText().toString();
                LoginUserAccount(email,password);
            }
        });
    }

    private void LoginUserAccount(String email, String password)
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Plz write your Email", Toast.LENGTH_SHORT).show();
        }


        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Plz write your Password", Toast.LENGTH_SHORT).show();
        }

        else
        {

            loaddingBar.setTitle("Login Account");
            loaddingBar.setMessage("Please wait, While we are verifying your credentials....");
            loaddingBar.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                String userId = mAuth.getCurrentUser().getUid();
                                Log.d("CurrentUserIdIs",userId);
                                Intent mainIntent = new Intent(LoginActivity.this,MapsActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mainIntent.putExtra("User_id",userId);
                                startActivity(mainIntent);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Wrong Email or Mobile Number,Plz check your Email and Mobile Number", Toast.LENGTH_SHORT).show();
                            }
                            loaddingBar.dismiss();
                        }
                    });

        }









    }
}

