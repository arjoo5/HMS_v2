package com.example.hp.hms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Plumber_Rates extends AppCompatActivity {
ListView menuList;
List<String> serviceList = new ArrayList<String>();
DatabaseReference db;
String[] selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber__rates);
        menuList = (ListView)findViewById(R.id.menuList);
        //serviceList.add("bj");
        //serviceList.add("bhjhkj");
        selectedItem=getIntent().getStringArrayExtra("details");
       // serviceList.add(selectedItem[1]);
        db=FirebaseDatabase.getInstance().getReference();
       DatabaseReference newC= db.child("Service_Provider Rates/"+selectedItem[1]);
       //serviceList.add(newC.toString());

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

    }

    private void display() {
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(Plumber_Rates.this,android.R.layout.simple_list_item_1,serviceList);
        menuList.setAdapter(serviceAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String option=serviceList.get(position);
                String[] toSendAhead= {option,selectedItem[1],selectedItem[0]};
                Intent  serviceChildIntent = new Intent(Plumber_Rates.this,ServiceRates.class).putExtra("details",toSendAhead);
                startActivity(serviceChildIntent);

            }
        });
    }
}
