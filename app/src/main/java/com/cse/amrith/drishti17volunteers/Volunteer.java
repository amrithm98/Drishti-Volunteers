package com.cse.amrith.drishti17volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Volunteer extends AppCompatActivity {
    Button reg,event,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg=(Button)findViewById(R.id.reg_vol);
        event=(Button)findViewById(R.id.event_vol);
        logout=(Button)findViewById(R.id.logout);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for volunteer Permission
                Intent intent=new Intent(Volunteer.this,Registration.class);
                startActivity(intent);
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Volunteer.this,EventVolunteer.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Volunteer.this,Login.class));

            }
        });
    }
}
