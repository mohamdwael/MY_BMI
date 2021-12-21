package com.example.my_bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddRecordActivity extends AppCompatActivity {
    private String weight,length,date,time;

    private DatabaseReference Ref;
    private ProgressDialog loadingBar;
    FirebaseAuth mAuth;
    EditText ed_weight,ed_length,ed_date,ed_time;
    Button btn_save_data_record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        mAuth = FirebaseAuth.getInstance();

        Ref = FirebaseDatabase.getInstance().getReference("BMI");



        btn_save_data_record =  findViewById(R.id.btn_save_data_record);
        ed_weight =  findViewById(R.id.ed_weight);
        ed_length =  findViewById(R.id.ed_length);
        ed_date =  findViewById(R.id.ed_date);
        ed_time =  findViewById(R.id.ed_time);



        loadingBar = new ProgressDialog(this);



        btn_save_data_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateRecordData();
            }
        });



    }


    private void ValidateRecordData() {
        weight = ed_weight.getText().toString();
        length = ed_length.getText().toString();
        date = ed_date.getText().toString();
        time = ed_time.getText().toString();


        if (TextUtils.isEmpty(weight)) {
            Toast.makeText(this, "Please write weight...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(length)) {
            Toast.makeText(this, "Please write length...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Please write date...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(time)) {
            Toast.makeText(this, "Please write time...", Toast.LENGTH_SHORT).show();
        } else {
            SaveRecordInfoToDatabase();
        }
    }




    private void SaveRecordInfoToDatabase() {
        HashMap<String, Object> ChaletMap = new HashMap<>();
        ChaletMap.put("weight", weight+" kg");
        ChaletMap.put("length", length+" cm");
        ChaletMap.put("date", date);
        ChaletMap.put("time", time);



        String key = FirebaseDatabase.getInstance().getReference("Users").push().getKey();
        Ref.child("Record").child(key).updateChildren(ChaletMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent idToList = new Intent(AddRecordActivity.this, MainActivity.class);
                            startActivity(idToList);

                            loadingBar.dismiss();
                            Toast.makeText(AddRecordActivity.this, "Chalet is added successfully..", Toast.LENGTH_SHORT).show();

                        } else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddRecordActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

