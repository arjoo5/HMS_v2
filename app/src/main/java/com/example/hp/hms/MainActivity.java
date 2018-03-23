package com.example.hp.hms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.hp.hms.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener nAuthListener;
    private DatabaseReference nDatabaseUsers,ngetDatabase;
    private TextView User;
    private ImageButton plumber;
    private ImageButton electrician;
    private ImageButton carpenter;
    private ImageButton pestcontrol;
    private ImageButton cleaner;
    private ImageButton painter;
    private ImageButton gardener;
    private ImageButton packersandmovers;
    private ImageButton appliancerepair;
    private EditText search;
    private Button ok;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       plumber  = (ImageButton)findViewById(R.id.plumber);
        electrician= (ImageButton)findViewById(R.id.electrician);
        carpenter = (ImageButton)findViewById(R.id.carpenter);
        pestcontrol = (ImageButton)findViewById(R.id.pestcontrol);
        cleaner = (ImageButton)findViewById(R.id.cleaner);
        painter = (ImageButton)findViewById(R.id.painter);
        gardener = (ImageButton)findViewById(R.id.gardener);
        packersandmovers = (ImageButton)findViewById(R.id.packersandmovers);
        appliancerepair = (ImageButton)findViewById(R.id.appliancerepair);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        User=(TextView) findViewById(R.id.user);
        search=(EditText)findViewById(R.id.search);
        ok=(Button)findViewById(R.id.bt);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent;
                String text1= search.getText().toString().trim();
                Log.d("hkjflrjfldjflfkd;frfl",text1);
                text1=text1.toLowerCase();
                if(TextUtils.isEmpty(text1))
                {
                    Toast.makeText(MainActivity.this, "Empty search field!", Toast.LENGTH_SHORT).show();
                }
                else
                if(text1.equals("plumber"))
                {
                    editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Plumber");
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                }
                else if(text1.equals("electrician"))
                {
                    editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Electrician");
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                }
                else if(text1.equals("carpenter"))
                {
                    editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Carpenter");
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                }
                else if(text1.equals("appliance repair"))
                {
                    editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Appliance Repair");
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                }
                else if(text1.equals("gardner"))
                {
                    editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Gardener");
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                }
                else if(text1.equals("pest control"))
                {
                     editIntent=new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Pest Control");
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);

                } else if(text1.equals("packers and movers"))
                {
                    editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Packers and Movers");
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                } else if(text1.equals("cleaner"))
                {
                    editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Cleaning");
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);

                } else if(text1.equals("painter"))
                {
                    editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Painting");
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Sorry! Service Not Available", Toast.LENGTH_SHORT).show();
                }





            }
        });
        nDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
        nDatabaseUsers.keepSynced(true);
        nAuth = FirebaseAuth.getInstance();
        nAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null) {
                    Intent loginIntent = new Intent(MainActivity.this, HomeScreen.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
                   else {
                    checkUserExist();


        String user_id=nAuth.getCurrentUser().getUid();

        ngetDatabase=nDatabaseUsers.child(user_id);
        ngetDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String edit_name = dataSnapshot.child("name").getValue(String.class);
                User.setText("Hi "+edit_name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
                }
            }
        };

        plumber.setOnClickListener(MainActivity.this);
        electrician.setOnClickListener(this);
        pestcontrol.setOnClickListener(this);
        packersandmovers.setOnClickListener(this);
        appliancerepair.setOnClickListener(this);
        gardener.setOnClickListener(this);
        carpenter.setOnClickListener(this);
        cleaner.setOnClickListener(this);
        painter.setOnClickListener(this);


    }


    protected void onStart() {
        super.onStart();

        nAuth.addAuthStateListener(nAuthListener);
    }

    private void checkUserExist() {

        final String user_id=nAuth.getCurrentUser().getUid();
        nDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChild(user_id)) {
                    Intent editIntent = new Intent(MainActivity.this,EditAccount.class);
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                logout();
                break;
            case R.id.editdetails:
                Intent myIntent0 = new Intent(this, EditAccount.class);
                this.startActivity(myIntent0);
                break;
            case R.id.aboutus:
                Intent myIntent1 = new Intent(this, AboutUs.class);
                this.startActivity(myIntent1);
                break;
            case R.id.feedback:
                Intent myIntent2 = new Intent(this, Feedback.class);
                this.startActivity(myIntent2);
                break;
            default:

        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        nAuth.signOut();
    }

    @Override
    public void onClick(View v) {
        Intent editIntent;
        switch (v.getId())
        {
            case R.id.plumber:  editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Plumber");
                editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(editIntent);
                break;
            case R.id.electrician: editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Electrician");
                editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(editIntent);
                break;
            case R.id.carpenter: editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Carpenter");
                editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(editIntent);
                break;
            case R.id.packersandmovers: editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Packers and Movers");
                editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(editIntent);
                break;
            case R.id.painter: editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Painting");
                editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(editIntent);
                break;
            case R.id.gardener: editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Gardener");
                editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(editIntent);
                break;
            case R.id.cleaner: editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Cleaning");
                editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(editIntent);
                break;
            case R.id.appliancerepair: editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Appliance Repair");
                editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(editIntent);
                break;
            case R.id.pestcontrol: editIntent = new Intent(MainActivity.this,UserSelectSubcategory.class).putExtra("service","Pest Control");
                editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(editIntent);
                break;

        }
    }
}
