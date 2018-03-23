package com.example.hp.hms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_activity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Login;
    private Button SignUP,Forget;
    private FirebaseAuth nAuth;
    private ProgressDialog nProgress;
    private DatabaseReference nDatabaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);



        nProgress=new ProgressDialog(this);

        nAuth = FirebaseAuth.getInstance();
        nDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users"); //gives reference to child "Users" of root directory of database
        nDatabaseUsers.keepSynced(true);

        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        Login = (Button) findViewById(R.id.Login);
        Forget=(Button) findViewById(R.id.forgetpassword);



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent4 = new Intent(Login_activity.this, ForgetPassword.class);
                Login_activity.this.startActivity(myIntent4);
            }
        });





        SignUP = (Button) findViewById(R.id.button7);
        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignup();
            }
        });

    }



    private void checkLogin() {
        String email=Email.getText().toString().trim();
        String password=Password.getText().toString().trim();
        if(email.equals("admin@gmail.com") && password.equals("12345"))
        {
            Intent AdminIntent = new Intent(Login_activity.this,Admin_Page.class);
            AdminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(AdminIntent);
        }
        else {
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                nProgress.setMessage("Checking Login....");
                nProgress.show();
                nAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            nProgress.dismiss();
                            checkUserExist();

                        } else {
                            nProgress.dismiss();
                            Toast.makeText(Login_activity.this, "Error Login", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Empty Fields", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkUserExist() {

        final String user_id=nAuth.getCurrentUser().getUid();
        nDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(user_id)) {
                    Intent mainIntent = new Intent(Login_activity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);
                }
                else
                {
                    Intent editIntent = new Intent(Login_activity.this,EditAccount.class);
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void openSignup(){
        Intent myIntent4 = new Intent(this, SignUp.class);
        this.startActivity(myIntent4);
    }

}
