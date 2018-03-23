package com.example.hp.hms;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeeFeedback extends AppCompatActivity {
    ListView menuList;
    List<String> serviceList = new ArrayList<String>();
    DatabaseReference db;
    Button delete;
    ArrayAdapter<String> serviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_feedback);
        menuList = (ListView)findViewById(R.id.menuList);
        delete = (Button) findViewById(R.id.Delete);
        db= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference newC= db.child("Feedback");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newC.setValue(null);
                serviceList.clear();
                serviceAdapter.notifyDataSetChanged();
            }
        });

        newC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot children:dataSnapshot.getChildren()) {
                    DataSnapshot fb1 = children.child("key");
                    serviceList.add(fb1.getValue(String.class));
                }
                display();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void display() {
        serviceAdapter = new ArrayAdapter<String>(SeeFeedback.this,android.R.layout.simple_list_item_1,serviceList);

        menuList.setAdapter(serviceAdapter);

    }
}
