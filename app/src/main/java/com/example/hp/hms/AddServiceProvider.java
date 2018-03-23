package com.example.hp.hms;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class AddServiceProvider extends AppCompatActivity {
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
        setContentView(R.layout.activity_add_service_provider);
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
                    public void onMenuSelected(int i) {
                        //Toast.makeText(AddServiceProvider.this,"you selected"+arrayName[i],Toast.LENGTH_LONG).show();
                        Intent DetailsIntent = new Intent(AddServiceProvider.this,service_provider_detail_form.class).putExtra("item selected",arrayName[i]);
                        DetailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(DetailsIntent);                    }
                });
    }
}
