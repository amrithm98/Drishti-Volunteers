package com.cse.amrith.drishti17volunteers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                if(Global.status==7 || Global.status==10){
                    Intent intent=new Intent(Volunteer.this,Registration.class);
                    startActivity(intent);
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(Volunteer.this).create();
                    alertDialog.setTitle("Unauthorized");
                    alertDialog.setMessage("You are not a Registration Desk volunteer!");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getApplicationContext(),"Login as Events Volunteer",Toast.LENGTH_SHORT).show();
                                }
                            });
                    alertDialog.show();
                }
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
