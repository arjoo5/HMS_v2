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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class getDetailsByAadhar extends AppCompatActivity {
    private DatabaseReference nDatabase,ngetDatabase;
    private Button Save;
    private EditText Name,Area,Street,Pincode,Phno;
    private FirebaseAuth nAuth;
    private ProgressDialog nProgress;
    private RadioGroup rg;
    String[] temp;
    public getDetailsByAadhar() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details_by_aadhar);

        Save = (Button) findViewById(R.id.Save);
        Name=(EditText)findViewById(R.id.input_name);
        Area=(EditText)findViewById(R.id.input_area);
        Street=(EditText)findViewById(R.id.input_street);
        Pincode=(EditText)findViewById(R.id.input_pincode);
        Phno=(EditText)findViewById(R.id.input_phno);

        temp=getIntent().getStringArrayExtra("option");

        nProgress= new ProgressDialog(this);
        rg = (RadioGroup) findViewById(R.id.gender_radio);
        nAuth = FirebaseAuth.getInstance();
        nDatabase= FirebaseDatabase.getInstance().getReference().child("Service Providers/"+temp[1]); //gives reference to child "Users" of root directory of database
        nDatabase.keepSynced(true);

        nDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            int check=0;
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot children:dataSnapshot.getChildren()) {
                    if(temp[0].equals(children.getKey()))
                    {
                        check=1;
                        String edit_name=children.child("name").getValue(String.class);
                        Name.setText(edit_name);
                        String edit_street = children.child("street").getValue(String.class);
                        Street.setText(edit_street);
                        String edit_area = children.child("area").getValue(String.class);
                        Area.setText(edit_area);
                        String edit_pincode = children.child("pincode").getValue(String.class);
                        Pincode.setText(edit_pincode);
                        String edit_phno = children.child("phone_no").getValue(String.class);
                        Phno.setText(edit_phno);

                        String edit_gender = children.child("gender").getValue(String.class);

                        RadioButton male=(RadioButton)findViewById(R.id.male_radio_btn);
                        RadioButton female=(RadioButton)findViewById(R.id.female_radio_btn);
                        if(edit_gender.equals("female")) {
                            female.setChecked(true);
                            male.setChecked(false);
                        }
                        else {
                            male.setChecked(true);
                            female.setChecked(false);
                        }
                    }

                }

                if(check==0)
                {
                    Toast.makeText(getDetailsByAadhar.this,"Invalid Aadhar Number!!",Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(getDetailsByAadhar.this,EditServiceProviders.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
            }
        });
    }

    void saveDetails()
    {

        String name=Name.getText().toString().trim();
        String street=Street.getText().toString().trim();
        String area=Area.getText().toString().trim();
        String pincode=Pincode.getText().toString().trim();
        String phno=Phno.getText().toString().trim();
        final String gender =
                ((RadioButton)findViewById(rg.getCheckedRadioButtonId()))
                        .getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(street)|| TextUtils.isEmpty(area)|| TextUtils.isEmpty(pincode)) {
            Toast.makeText(this,"Empty Fields",Toast.LENGTH_LONG).show();
        }
        else{
            nProgress.setMessage("Saving Details");
            nProgress.show();
            ngetDatabase=nDatabase.child(temp[0]);
            ngetDatabase.child("name").setValue(name);
            ngetDatabase.child("street").setValue(street);
            ngetDatabase.child("area").setValue(area);
            ngetDatabase.child("pincode").setValue(pincode);
            ngetDatabase.child("phone_no").setValue(phno);
            ngetDatabase.child("gender").setValue(gender);

            nProgress.dismiss();
            Toast.makeText(this,"Saved your details",Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(getDetailsByAadhar.this,Admin_Page.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
        }

    }

    }

