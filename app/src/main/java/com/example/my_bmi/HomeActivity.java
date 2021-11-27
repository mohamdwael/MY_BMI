package com.example.my_bmi;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;


import com.example.my_bmi.adapter.AdapterOldStatus;
import com.example.my_bmi.model.OldStatues;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView list;
    AdapterOldStatus adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list = findViewById(R.id.list);
        ArrayList<OldStatues> models = new ArrayList<>();
        models.add(new OldStatues("20/1/2020","60 Kg","170 Cm"));
        models.add(new OldStatues("20/1/2020","60 Kg","170 Cm"));
        models.add(new OldStatues("20/1/2020","60 Kg","170 Cm"));
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterOldStatus(models, this);
        list.setAdapter(adapter);
    }
}