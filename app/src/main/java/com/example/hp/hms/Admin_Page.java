package com.example.hp.hms;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class Admin_Page extends AppCompatActivity {
    RelativeLayout bar1;
    private Button add;
    private Button edit;
    private Button delete;
    private Button feedback;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__page);
        add = (Button) findViewById(R.id.Add);
        edit = (Button) findViewById(R.id.Edit);
        delete = (Button) findViewById(R.id.Delete);
        feedback = (Button) findViewById(R.id.SeeFd);
        logout = (Button) findViewById(R.id.Logout);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DetailsIntent = new Intent(Admin_Page.this, AddServiceProvider.class);
                startActivity(DetailsIntent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DetailsIntent = new Intent(Admin_Page.this, EditServiceProviders.class);
                startActivity(DetailsIntent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DetailsIntent = new Intent(Admin_Page.this, DeleteServiceProviders.class);
                startActivity(DetailsIntent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DetailsIntent = new Intent(Admin_Page.this, SeeFeedback.class);
                startActivity(DetailsIntent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DetailsIntent = new Intent(Admin_Page.this, HomeScreen.class);
                startActivity(DetailsIntent);
            }
        });






    }
}
