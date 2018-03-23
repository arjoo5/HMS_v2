package com.example.hp.hms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserSelectSubcategory extends AppCompatActivity {
    ListView menuList;
    List<String> serviceList = new ArrayList<String>();
    String selectedItem;
    DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select_subcategory);
        menuList = (ListView)findViewById(R.id.menuList);
        //serviceList.add("bj");
        //serviceList.add("bhjhkj");
        selectedItem=getIntent().getStringExtra("service");
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        selectedItem=getIntent().getStringExtra("service");

        db= FirebaseDatabase.getInstance().getReference();
        DatabaseReference newC= db.child("Service_Provider Rates/"+selectedItem);
        newC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()) {

                    serviceList.add(child.getKey());
                }
                display();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //serviceList.add(newC.toString());


    }

    private void display() {
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(UserSelectSubcategory.this,android.R.layout.simple_list_item_1,serviceList);
        menuList.setAdapter(serviceAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String option=serviceList.get(position);
                String[] toSendAhead= {selectedItem,option};
                Intent serviceChildIntent = new Intent(UserSelectSubcategory.this,UserAddressDetail.class).putExtra("details",toSendAhead);
                startActivity(serviceChildIntent);

            }
        });
    }
}
