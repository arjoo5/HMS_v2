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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FetchRates extends AppCompatActivity {
    ListView menuList;
    List<customlist_tofetchratesbyuser> serviceList = new ArrayList<customlist_tofetchratesbyuser>();
    String[] selectedItem;
    DatabaseReference db,dbsave,dbsave1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_rates);
        menuList = (ListView)findViewById(R.id.menuList);
        String[] options=getIntent().getStringArrayExtra("details");

        db= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference newC= db.child("Service Providers/"+options[0]+"/"+options[2]+"/"+options[1]);
        Log.d("main", newC.toString());


        class customAdapter extends ArrayAdapter<customlist_tofetchratesbyuser> {
            public customAdapter() {

                super(FetchRates.this,R.layout.customlist_tofetchratesbyuser,serviceList);
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if(convertView==null)
                {
                    convertView=getLayoutInflater().inflate(R.layout.customlist_tofetchratesbyuser,parent,false);
                }

                TextView myTextView = (TextView)convertView.findViewById(R.id.name);
                final TextView myRate = (TextView)convertView.findViewById(R.id.tv);
                final customlist_tofetchratesbyuser currentRate = serviceList.get(position);
                myTextView.setText(currentRate.getService());
                myRate.setText(String.valueOf(currentRate.getRate()));

                return convertView;
            }
        }


        newC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childs:dataSnapshot.getChildren()) {
                    String key = childs.getKey();
                    int rate=childs.getValue(Integer.class);

                    serviceList.add(new customlist_tofetchratesbyuser(key,rate));
                }
                display();
            }
            private void display()
            {
                ArrayAdapter<customlist_tofetchratesbyuser> serviceAdapter = new customAdapter();
                menuList.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
