package com.example.hp.hms;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceRates extends AppCompatActivity {
    ListView menuList;
    List<ListItems> serviceList = new ArrayList<ListItems>();
    String selectedItem;
    DatabaseReference db,dbsave,dbsave1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_rates);
        menuList = (ListView)findViewById(R.id.menuList);
        String[] options=getIntent().getStringArrayExtra("details");
        //serviceList.add("bj");
        //serviceList.add("bhjhkj");

        //serviceList.add(new ListItems(options[0],0));
        db=FirebaseDatabase.getInstance().getReference();
        dbsave=FirebaseDatabase.getInstance().getReference();
        final DatabaseReference newC= db.child("Service_Provider Rates/"+options[1]+"/"+options[0]);

        Log.d("db",db.toString());
        Log.d("dbsave",dbsave.toString());
        String id=options[2];
        Log.d("check",id);
        dbsave1=dbsave.child("Service Providers/"+options[1]+"/"+id+"/"+options[0]);
        Log.d("dbsave1",dbsave1.toString());
      //  Log.d("path",dbsave.toString());
        //serviceList.add(newC.toString());
        class customAdapter extends ArrayAdapter<ListItems>{
            public customAdapter() {

                super(ServiceRates.this,R.layout.customlistitem,serviceList);
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if(convertView==null)
                {
                    convertView=getLayoutInflater().inflate(R.layout.customlistitem,parent,false);
                }
                final ListItems currentRate = serviceList.get(position);
                TextView myTextView = (TextView)convertView.findViewById(R.id.name);
                final EditText myEditText = (EditText)convertView.findViewById(R.id.et);

                myTextView.setText(currentRate.getService());
                myEditText.setText(String.valueOf(currentRate.getRate()));
                myEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if(!hasFocus) {
                            String text = myEditText.getText().toString();
                            int newRate = Integer.parseInt(text);

                            dbsave1.child(currentRate.getService()).setValue(newRate);

                        }

                    }
                });
                return convertView;
            }
        }







        newC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childs:dataSnapshot.getChildren()) {
                    String key = childs.getKey();
                   int rate=childs.getValue(Integer.class);

                    serviceList.add(new ListItems(key,rate));
                }
                display();
            }
            private void display()
            {
                ArrayAdapter<ListItems> serviceAdapter = new customAdapter();
                menuList.setAdapter(serviceAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
