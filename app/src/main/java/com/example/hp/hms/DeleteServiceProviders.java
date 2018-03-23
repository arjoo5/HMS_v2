package com.example.hp.hms;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class DeleteServiceProviders extends AppCompatActivity {
    Button fetch;
    EditText Ano;
    DatabaseReference db;
    String arrayName[]= {"Carpenter",
            "Painting",
            "Cleaning",
            "Packers and Movers",
            "Electrician",
            "Appliance Repair",
            "Plumber",
            "Pest Control",
            "Gardner"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_service_providers);


        CircleMenu circleMenu = (CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#ffffff"),R.drawable.add,R.drawable.cross)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.carpenter)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.painter)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.cleaning)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.packers)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.electrician)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.appliance)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.plumber)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.pest)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.gardener)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(final int i) {
                        //Toast.makeText(AddServiceProvider.this,"you selected"+arrayName[i],Toast.LENGTH_LONG).show();
                        AlertDialog.Builder mBuilder=new AlertDialog.Builder(DeleteServiceProviders.this);
                        View mView=getLayoutInflater().inflate(R.layout.getaadhar,null);
                        Ano=(EditText) mView.findViewById(R.id.Ano);
                        fetch=(Button) mView.findViewById(R.id.Fetch);
                        db= FirebaseDatabase.getInstance().getReference();
                        final DatabaseReference newC= db.child("Service Providers/"+arrayName[i]);
                        fetch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String ano=Ano.getText().toString();

                                newC.addValueEventListener(new ValueEventListener() {
                                    int check=0;
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for(DataSnapshot children:dataSnapshot.getChildren()) {

                                            if(ano.equals(children.getKey()))
                                            {
                                               DatabaseReference toDelete=db.child("Service Providers/"+arrayName[i]+"/"+children.getKey());
                                               toDelete.setValue(null);
                                                Toast.makeText(DeleteServiceProviders.this,"Service Provider Deleted!",Toast.LENGTH_SHORT).show();
                                                check=1;
                                                Intent DetailsIntent = new Intent(DeleteServiceProviders.this,Admin_Page.class);
                                                DetailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(DetailsIntent);
                                                break;
                                            }


                                        }

                                        if(check!=1)
                                        {
                                            Toast.makeText(DeleteServiceProviders.this,"Service Provider not found!",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }
                        });
                        mBuilder.setView(mView);
                        AlertDialog dialog=mBuilder.create();
                        dialog.show();

                                           }
                });

    }
}
