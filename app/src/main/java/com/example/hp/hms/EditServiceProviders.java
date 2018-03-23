package com.example.hp.hms;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditServiceProviders extends AppCompatActivity {

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
        setContentView(R.layout.activity_edit_service_providers);
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
                        AlertDialog.Builder mBuilder=new AlertDialog.Builder(EditServiceProviders.this);
                        View mView=getLayoutInflater().inflate(R.layout.getaadhar,null);
                        Ano=(EditText) mView.findViewById(R.id.Ano);
                        fetch=(Button) mView.findViewById(R.id.Fetch);final String ano=Ano.getText().toString();
                        fetch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String[] options=new String[2];
                                options[1]=arrayName[i];
                                final String ano=Ano.getText().toString();

                                options[0]=ano;
                                Intent DetailsIntent = new Intent(EditServiceProviders.this,getDetailsByAadhar.class).putExtra("option",options);
                                DetailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(DetailsIntent);
                            }
                        });

                        mBuilder.setView(mView);
                        AlertDialog dialog=mBuilder.create();
                        dialog.show();

                    }
                });
    }
}
