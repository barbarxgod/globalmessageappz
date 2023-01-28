package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button sign, login;
    FirebaseAuth mAuth;
    ProgressBar pBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        login=findViewById(R.id.lgnBtn);
        sign=findViewById(R.id.sgnBtn);

        mAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(view ->{
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
        sign.setOnClickListener(view ->{
            String email= editTextEmail.getText().toString();
            String password=editTextPassword.getText().toString();
            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this,"Lütfen girdileri boş bırakmayınız.", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task->{
                if (task.isSuccessful()){
                    Toast.makeText(this,"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(this,"Kayıt Başarısız",Toast.LENGTH_SHORT).show();
                }


            });


        });


    }
}