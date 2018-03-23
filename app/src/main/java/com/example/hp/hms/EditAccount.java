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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAccount extends AppCompatActivity {
    private DatabaseReference nDatabase,ngetDatabase;
    private Button Save;
    private EditText Name,Age;
    private FirebaseAuth nAuth;
    private ProgressDialog nProgress;
    private RadioGroup rg;



    public EditAccount() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Save = (Button) findViewById(R.id.save);
        Name=(EditText)findViewById(R.id.signup_input_name);
        Age=(EditText)findViewById(R.id.signup_input_age);
        nProgress= new ProgressDialog(this);
        rg = (RadioGroup) findViewById(R.id.gender_radio_group);
        nAuth = FirebaseAuth.getInstance();
        nDatabase= FirebaseDatabase.getInstance().getReference().child("Users"); //gives reference to child "Users" of root directory of database
        nDatabase.keepSynced(true);
        String user_id=nAuth.getCurrentUser().getUid();

        ngetDatabase=nDatabase.child(user_id);

        ngetDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String edit_name = dataSnapshot.child("name").getValue(String.class);
                Name.setText(edit_name);
                String edit_age = dataSnapshot.child("age").getValue(String.class);
                Age.setText(edit_age);
                String edit_gender = dataSnapshot.child("gender").getValue(String.class);
                Log.d("g:",edit_gender);
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
        String age=Age.getText().toString().trim();
        final String gender =
                ((RadioButton)findViewById(rg.getCheckedRadioButtonId()))
                        .getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(age)) {
            Toast.makeText(this,"Empty Fields",Toast.LENGTH_LONG).show();
        }
        else{
            nProgress.setMessage("Saving Details");
            nProgress.show();

            String user_id=nAuth.getCurrentUser().getUid();
            DatabaseReference current_user_db=nDatabase.child(user_id);
            current_user_db.child("name").setValue(name);
            current_user_db.child("age").setValue(age);
            current_user_db.child("gender").setValue(gender);
            nProgress.dismiss();
            Toast.makeText(this,"Saved your details",Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(EditAccount.this,MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
        }

    }
}
