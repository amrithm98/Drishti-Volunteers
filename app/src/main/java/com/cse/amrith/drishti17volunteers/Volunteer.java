package com.cse.amrith.drishti17volunteers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Utils.QR;
import com.google.firebase.auth.FirebaseAuth;

public class Volunteer extends AppCompatActivity {
    TextView reg,event,logout,score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg=(TextView)findViewById(R.id.reg_vol);
        event=(TextView)findViewById(R.id.event_vol);
        logout=(TextView)findViewById(R.id.logout);
        score=(TextView)findViewById(R.id.score);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for volunteer Permission
                if(Global.status==7 || Global.status==10|| Global.status==9){
                    Intent intent=new Intent(Volunteer.this,QR.class);
                    intent.putExtra("Volunteer","reg");
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
                Intent intent=new Intent(Volunteer.this,QR.class);
                intent.putExtra("Volunteer","event");
                startActivity(intent);
            }
        });
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Volunteer.this,QR.class);
                intent.putExtra("Volunteer","update");
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
