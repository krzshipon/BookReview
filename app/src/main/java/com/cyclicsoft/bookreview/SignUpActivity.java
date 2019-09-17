package com.cyclicsoft.bookreview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail, etPass;
    Button btSignUp;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUi();


        btSignUp.setOnClickListener((view)-> addUser());
    }

    private void addUser() {
        String email =  etEmail.getText().toString();
        String pass = etPass.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Please check username / pass!!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Sign up success!!", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void initUi() {
        etEmail = findViewById(R.id.et_emails);
        etPass = findViewById(R.id.et_passs);
        btSignUp = findViewById(R.id.bt_signup);

        mAuth = FirebaseAuth.getInstance();

    }
}
