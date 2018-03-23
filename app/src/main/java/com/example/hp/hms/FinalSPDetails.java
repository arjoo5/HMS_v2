package com.example.hp.hms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FinalSPDetails extends AppCompatActivity {
    ListView menuList;
    List<ListItemProvider> serviceList = new ArrayList<ListItemProvider>();
    String selectedItem;
    DatabaseReference db,dbsave,dbsave1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_spdetails);
        menuList = (ListView)findViewById(R.id.menuList);
        final String[] options=getIntent().getStringArrayExtra("details");
        //serviceList.add("bj");
        //serviceList.add("bhjhkj");

        //serviceList.add(new ListItems(options[0],0));
        db=FirebaseDatabase.getInstance().getReference();
        dbsave=FirebaseDatabase.getInstance().getReference();
        final DatabaseReference newC= db.child("Service Providers/"+options[0]);


        class customAdapter extends ArrayAdapter<ListItemProvider>{
            public customAdapter() {

                super(FinalSPDetails.this,R.layout.customlistitem_toshowspdetails,serviceList);
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if(convertView==null)
                {
                    convertView=getLayoutInflater().inflate(R.layout.customlistitem_toshowspdetails,parent,false);
                }
                final ListItemProvider currentSP = serviceList.get(position);
                TextView myTextView1 = (TextView)convertView.findViewById(R.id.name);
                TextView myTextView2 = (TextView)convertView.findViewById(R.id.gender);
                TextView myTextView3 = (TextView)convertView.findViewById(R.id.phno);
                TextView myTextView4 = (TextView)convertView.findViewById(R.id.street);
                TextView myTextView5 = (TextView)convertView.findViewById(R.id.area);
                TextView myTextView6 = (TextView)convertView.findViewById(R.id.ano);
                Button fetch_rates = (Button)convertView.findViewById(R.id.fetch_rates);

                myTextView1.setText(currentSP.getNameSP());
                myTextView2.setText(currentSP.getGender());
                myTextView3.setText(currentSP.getPhno());
                myTextView4.setText(currentSP.getStreet());
                myTextView5.setText(currentSP.getArea());
                myTextView6.setText(currentSP.getAno());


                fetch_rates.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        options[2]=currentSP.getAno();
                        Intent serviceChildIntent = new Intent(FinalSPDetails.this,FetchRates.class).putExtra("details",options);
                        startActivity(serviceChildIntent);
                    }
                });


                return convertView;
            }
        }







        newC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child_ano:dataSnapshot.getChildren()) {



                    String name = child_ano.child("name").getValue(String.class);
                    String gender = child_ano.child("gender").getValue(String.class);
                    String phno = child_ano.child("phone_no").getValue(String.class);
                    String street = child_ano.child("street").getValue(String.class);
                    String area = child_ano.child("area").getValue(String.class);
                    String pincode = child_ano.child("pincode").getValue(String.class);
                    String ano = child_ano.getKey();

                   // if(pincode.equals(options[2]))
                    serviceList.add(new ListItemProvider(name,gender,phno,street,area,ano));

                }
                display();
            }

            private void display()
            {
                if(serviceList.size()==0)
                {
                    AlertDialog.Builder mBuilder=new AlertDialog.Builder(FinalSPDetails.this);
                    View mView=getLayoutInflater().inflate(R.layout.noprovider,null);
                    TextView Message=(TextView) mView.findViewById(R.id.tv);
                    Button viewOthers=(Button) mView.findViewById(R.id.bt);
                    mBuilder.setView(mView);
                    AlertDialog dialog=mBuilder.create();
                    dialog.show();
                    viewOthers.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent serviceChildIntent = new Intent(FinalSPDetails.this,UserAddressDetail.class).putExtra("details",options);
                            startActivity(serviceChildIntent);
                        }
                    });

                }
                else {
                    ArrayAdapter<ListItemProvider> serviceAdapter = new customAdapter();
                    menuList.setAdapter(serviceAdapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
