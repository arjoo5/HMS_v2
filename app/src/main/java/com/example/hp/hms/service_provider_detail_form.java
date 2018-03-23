package com.example.hp.hms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class service_provider_detail_form extends AppCompatActivity {
    private DatabaseReference nDatabase,ngetDatabase;
    private Button SaveDetails;
    private EditText Name,Street,Area,Pincode,Phno,Ano;
    private ProgressDialog nProgress;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_detail_form);
        nDatabase= FirebaseDatabase.getInstance().getReference().child("Service Providers");

        //gives reference to child "Users" of root directory of database
        nDatabase.keepSynced(true);
        final String selectedItem=getIntent().getStringExtra("item selected");
        SaveDetails = (Button) findViewById(R.id.savedetails);
        Name=(EditText)findViewById(R.id.input_name);
        Street=(EditText)findViewById(R.id.input_street);
        Area=(EditText)findViewById(R.id.input_area);
        Pincode=(EditText)findViewById(R.id.input_pincode);
        Phno=(EditText)findViewById(R.id.input_phno);
        Ano=(EditText)findViewById(R.id.input_aadharno);
        rg = (RadioGroup) findViewById(R.id.gender_radio);
       // nProgress= new ProgressDialog(this);


        SaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Name.getText().toString().trim();
                String street=Street.getText().toString().trim();
                String area=Area.getText().toString().trim();
                String pincode=Pincode.getText().toString().trim();
                String phno=Phno.getText().toString().trim();
                String ano=Ano.getText().toString().trim();
                final String gender =
                        ((RadioButton)findViewById(rg.getCheckedRadioButtonId()))
                                .getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(street) || TextUtils.isEmpty(area) || TextUtils.isEmpty(pincode)
                        || TextUtils.isEmpty(phno) || TextUtils.isEmpty(ano)) {
                    Toast.makeText(service_provider_detail_form.this,"Empty Fields",Toast.LENGTH_LONG).show();
                }
                else {
               //     nProgress.setMessage("Saving Details");
                 //   nProgress.show();


                    DatabaseReference childRef = nDatabase.child(selectedItem);
                    DatabaseReference idref = childRef.child(ano);
                    idref.child("name").setValue(name);
                    idref.child("street").setValue(street);
                    idref.child("area").setValue(area);
                    idref.child("pincode").setValue(pincode);
                    idref.child("phone_no").setValue(phno);
                    idref.child("aadhar_no").setValue(ano);
                    idref.child("gender").setValue(gender);

                    Toast.makeText(service_provider_detail_form.this,"Saved your details",Toast.LENGTH_LONG).show();
                  //  nProgress.dismiss();
                    Intent mainIntent;
                    String[] spDetails = new String[] {ano.toString(), selectedItem};
                   mainIntent = new Intent(service_provider_detail_form.this,Plumber_Rates.class).putExtra("details",spDetails);
                   startActivity(mainIntent);


                }
            }
        });
    }


}
