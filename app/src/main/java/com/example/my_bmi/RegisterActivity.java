package com.example.my_bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    TextView go_to_login;
    private EditText ed_name,ed_email,ed_Password,ed_Re_Password;
    private Button create;
    FirebaseAuth mAuth;
    String Email, Password,Uid,Name,Re_Password;
    private FirebaseDatabase database;
    private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_name =findViewById(R.id.ed_name);
        ed_email =findViewById(R.id.ed_email);
        ed_Password =findViewById(R.id.ed_password_sginup);
        ed_Re_Password =findViewById(R.id.ed_re_password_sginup);
        ed_Re_Password =findViewById(R.id.ed_re_password_sginup);
        go_to_login = findViewById(R.id.go_to_login);
        create = findViewById(R.id.create);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        rootRef=database.getReference("BMI");


        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerTenant();
            }
        });

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(TenantSingUpActivity.this,TenantSingUpOrLoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });


    }

    private void registerTenant() {
        Email = ed_email.getText().toString().trim();
        Password = ed_Password.getText().toString().trim();
        Re_Password= ed_Re_Password.getText().toString().trim();
        Name = ed_name.getText().toString().trim();
        if (Email.isEmpty()) {
            ed_email.setError("Please Enter Email");
            ed_email.requestFocus();
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Password.equals(Re_Password)) {
            if (Password.isEmpty()) {
                ed_Password.setError("Please Enter Password");
                ed_Password.requestFocus();
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Password.length() < 6) {
                ed_Password.setError("Minimum length of password should be 6");
                ed_Password.requestFocus();
                Toast.makeText(this, "Minimum length of password should be 6", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Uid = mAuth.getCurrentUser().getUid();
                    finish();
                    HashMap tenantDetails =new HashMap();
                    tenantDetails.put("Uid",Uid);
                    tenantDetails.put("name",Name);
                    tenantDetails.put("email",Email);
                    tenantDetails.put("password",Password+"");

                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    intent.putExtra("name",Name);
                    startActivity(intent);
                    rootRef.child("Users").child(Uid).setValue(tenantDetails);
                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });
    }
}