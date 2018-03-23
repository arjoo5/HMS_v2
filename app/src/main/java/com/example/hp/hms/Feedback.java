package com.example.hp.hms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {
    private DatabaseReference nDatabase,ngetDatabase;
    EditText Feedback;
    Button SaveFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        SaveFeedback=(Button)findViewById(R.id.SaveFb);
        Feedback=(EditText) findViewById(R.id.editText2);
        nDatabase= FirebaseDatabase.getInstance().getReference().child("Feedback"); //gives reference to child "Users" of root directory of database
        nDatabase.keepSynced(true);
        SaveFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFeedback();
            }
        });
    }

    private void saveFeedback() {
        String name=Feedback.getText().toString().trim();
        if(TextUtils.isEmpty(name) ) {
            Toast.makeText(this,"Empty Fields",Toast.LENGTH_LONG).show();
        }
        else{
            nDatabase.push().child("key").setValue(name);

            Toast.makeText(this,"Saved your details",Toast.LENGTH_LONG).show();

        }
    }
}
