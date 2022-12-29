package com.example.licentabuna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity  {
    private EditText mID;
    private EditText mPassword;
    private Button mloginBtn;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mID = (EditText) findViewById(R.id.ID);
        mPassword = (EditText) findViewById(R.id.Pw);
        mloginBtn = (Button) findViewById(R.id.loginBtn);
        mloginBtn.setOnClickListener(v -> {userLogin();});

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        fAuth=FirebaseAuth.getInstance();


        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.loginBtn:
                        if(fAuth!=null) {
                            startActivity(new Intent(Login.this, MainFrame.class));
                            userLogin();
                        }else{
                            startActivity(new Intent(Login.this,Login.class));
                            Toast.makeText(Login.this, "Datele nu au fost introduse ", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
    }


    private void userLogin() {
        String id = mID.getText().toString().trim();
        String pass = mPassword.getText().toString().trim();


        if (TextUtils.isEmpty(id)) {
            mID.setError("Adaugati emailul dvs");
            mID.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            mID.setError("Adauga-ti o adresa valida de email");
            mID.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            mPassword.setError("Adaugati parola");
            mPassword.requestFocus();
            return;
        }

        if (pass.length() < 6) {
            mPassword.setError("Parola trebuia sa aiba 6 sau mai multe caractere");
            mPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);



        fAuth.signInWithEmailAndPassword(id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();*/

             /*   if(user.isEmailVerified()){
                    startActivity(new Intent(Login.this,MainFrame.class));
                }else{
                    user.sendEmailVerification();
                    Toast.makeText(Login.this,"Verifica-ti emailul pentru verificare",Toast.LENGTH_LONG).show();
                }*/

                if(task.isSuccessful()){
                    //redirect spre profil user
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainFrame.class));

                }else{
                    task.isCanceled();
                        Toast.makeText(Login.this, "Verifica-ti-va datele de autentificare", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Login.this, Login.class));
                }
            }
        });
    }
};


