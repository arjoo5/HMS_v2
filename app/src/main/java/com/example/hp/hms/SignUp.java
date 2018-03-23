package com.example.hp.hms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private Button Login;
    private Button SignUP;
    private EditText Name,Email,Password,Age;
    private FirebaseAuth nAuth;
    private ProgressDialog nProgress;
    private DatabaseReference nDatabase;
    private RadioGroup rg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Login = (Button) findViewById(R.id.btn_link_login);
        SignUP = (Button) findViewById(R.id.btn_signup);
        Name=(EditText)findViewById(R.id.signup_input_name);
        Email=(EditText)findViewById(R.id.signup_input_email);
        Password=(EditText)findViewById(R.id.signup_input_password);
        Age=(EditText)findViewById(R.id.signup_input_age);
        nProgress= new ProgressDialog(this);
        rg = (RadioGroup) findViewById(R.id.gender_radio_group);



        nAuth = FirebaseAuth.getInstance();
        nDatabase= FirebaseDatabase.getInstance().getReference().child("Users"); //gives reference to child "Users" of root directory of database
        nDatabase.keepSynced(true);

        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignup();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });


    }


    public void openSignup(){

        final String name=Name.getText().toString().trim();
        String email=Email.getText().toString().trim();
        String password=Password.getText().toString().trim();
        final String age=Age.getText().toString().trim();
      final String gender =
               ((RadioButton)findViewById(rg.getCheckedRadioButtonId()))
                      .getText().toString();


        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(age)) {
            Toast.makeText(this,"Empty Fields",Toast.LENGTH_LONG).show();
        }
        else
        {
            nProgress.setMessage("Signing up....");
            nProgress.show();
            nAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String user_id=nAuth.getCurrentUser().getUid();
                        Log.d("hello",user_id);
                        DatabaseReference current_user_db=nDatabase.child(user_id);
                        current_user_db.child("name").setValue(name);
                        current_user_db.child("age").setValue(age);
                       current_user_db.child("gender").setValue(gender);
                        nProgress.dismiss();

                        Intent mainIntent = new Intent(SignUp.this,MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);

                    }
                }
            });
        }


    }
    public void openLogin(){
        Intent myIntent = new Intent(this, Login_activity
                .class);
        this.startActivity(myIntent);
    }
}
