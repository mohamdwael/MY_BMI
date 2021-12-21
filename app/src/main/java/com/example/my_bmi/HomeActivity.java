package com.example.my_bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_bmi.adapter.AdapterOldStatus;
import com.example.my_bmi.model.OldStatues;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView list;
    AdapterOldStatus adapter;
    Button btn_add_record,btn_add_food,btn_view_food;
    TextView txt_name_home;
    String name;
    OldStatues oldStatues;
    DatabaseReference Ref;
    TextView txt_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        name = getIntent().getExtras().getString("name");


        Ref = FirebaseDatabase.getInstance().getReference().child("BMI").child("Record");
        list = findViewById(R.id.list);
        btn_add_record = findViewById(R.id.btn_add_record);
        btn_add_food = findViewById(R.id.btn_add_food);
        btn_view_food = findViewById(R.id.btn_view_food);
        txt_name_home = findViewById(R.id.txt_name_home);
        txt_logout = findViewById(R.id.txt_logout);
        txt_name_home.setText("Hi, " + name);

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_add_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddRecordActivity.class);
                startActivity(intent);
            }
        });

        btn_view_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewFoodActivity.class);
                startActivity(intent);
            }
        });

        btn_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddFoodDetailsActivity.class);
                startActivity(intent);
            }
        });


        final ArrayList<OldStatues> models = new ArrayList<>();
        list.setLayoutManager(new LinearLayoutManager(HomeActivity.this));


        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    models.add(oldStatues);

                }
                adapter = new AdapterOldStatus(models, HomeActivity.this);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
