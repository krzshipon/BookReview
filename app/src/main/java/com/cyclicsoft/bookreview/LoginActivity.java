package com.cyclicsoft.bookreview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPass;
    Button btLogin;
    TextView tvSignup;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();


        btLogin.setOnClickListener(view -> {
            String email =  etEmail.getText().toString();
            String pass = etPass.getText().toString();
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                Toast.makeText(this, "Please check username / pass!!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(authResult -> {
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Login failed!! please check username or password.", Toast.LENGTH_SHORT).show();
                    });
        });

        tvSignup.setOnClickListener(view -> {
            Intent signupIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(signupIntent);
            finish();
        });
    }

    private void initUi() {
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
        btLogin = findViewById(R.id.bt_login);
        tvSignup = findViewById(R.id.tv_goto_signup);

    }
}
