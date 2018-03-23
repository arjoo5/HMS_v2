package com.example.hp.hms;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class HomeScreen extends AppCompatActivity {
    String arrayName[]= {"Sign Up",
            "Login",
            "About Us",

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        CircleMenu circleMenu = (CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#ffffff"),R.drawable.click,R.drawable.cross)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.signnup)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.login)
                .addSubMenu(Color.parseColor("#ffffff"),R.drawable.aboutus)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {
                        if(i==0) {
                            Intent DetailsIntent = new Intent(HomeScreen.this, SignUp.class);
                            DetailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(DetailsIntent);
                        }
                        else
                        if(i==1) {
                            Intent DetailsIntent = new Intent(HomeScreen.this, Login_activity.class);
                            DetailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(DetailsIntent);
                        }
                        else
                        if(i==2) {
                            Intent DetailsIntent = new Intent(HomeScreen.this, AboutUs.class);
                            DetailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(DetailsIntent);
                        }
                    }
                });
    }
}
