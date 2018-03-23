package com.example.hp.hms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserAddressDetail extends AppCompatActivity {
    EditText area,street,pincode;
    Button showdetails;
    String[] selectedOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address_detail);
        selectedOptions=getIntent().getStringArrayExtra("details");

        area = (EditText)findViewById(R.id.input_area);
        street = (EditText)findViewById(R.id.input_street);
        pincode = (EditText)findViewById(R.id.input_pincode);
        showdetails = (Button)findViewById(R.id.showdetails);
        showdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pcode=pincode.getText().toString();
                String[] finalOptions={selectedOptions[0],selectedOptions[1],pcode};
                if(!TextUtils.isEmpty(pcode))
                {
                    Intent serviceChildIntent = new Intent(UserAddressDetail.this,FinalSPDetails.class).putExtra("details",finalOptions);
                    startActivity(serviceChildIntent);
                }
                else
                {
                    Toast.makeText(UserAddressDetail.this, "Enter pincode!!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}
