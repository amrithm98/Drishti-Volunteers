package com.cse.amrith.drishti17volunteers;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;
import com.cse.amrith.drishti17volunteers.Utils.QR;
import com.google.firebase.auth.FirebaseAuth;

public class Volunteer extends AppCompatActivity {
    TextView reg, event, logout, score, eventCoordinator,notif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg = (TextView) findViewById(R.id.reg_vol);
        event = (TextView) findViewById(R.id.event_vol);
        logout = (TextView) findViewById(R.id.logout);
        score = (TextView) findViewById(R.id.score);
        notif = (TextView) findViewById(R.id.notif);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i("version", "M");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Log.i("permission", "no");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            } else
                Log.i("permission", "yes");
        }
        eventCoordinator = (TextView) findViewById(R.id.eventAdmin);
        eventCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.status >= 8) {
                    Intent intent = new Intent(Volunteer.this, EventCoordinator.class);
                    startActivity(intent);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Volunteer.this).create();
                    alertDialog.setTitle("Unauthorized");
                    alertDialog.setMessage("You are not an Event Coordinator!");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(getApplicationContext(),"Login as Events Volunteer",Toast.LENGTH_SHORT).show();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for volunteer Permission
                if (Global.status == 7 || Global.status > 8) {
                    Intent intent = new Intent(Volunteer.this, QR.class);
                    intent.putExtra("Volunteer", "reg");
                    startActivity(intent);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Volunteer.this).create();
                    alertDialog.setTitle("Unauthorized");
                    alertDialog.setMessage("You are not a Registration Desk volunteer!");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                        Toast.makeText(getApplicationContext(),"Login as Events Volunteer",Toast.LENGTH_SHORT).show();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Volunteer.this, QR.class);
                intent.putExtra("Volunteer", "event");
                startActivity(intent);
            }
        });
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.status >= 8) {
                    Intent intent = new Intent(Volunteer.this, QR.class);
                    intent.putExtra("Volunteer", "update");
                    startActivity(intent);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Volunteer.this).create();
                    alertDialog.setTitle("Unauthorized");
                    alertDialog.setMessage("You are not an event coordinator admin");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                        Toast.makeText(getApplicationContext(),"Login as Events Volunteer",Toast.LENGTH_SHORT).show();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
        notif.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Global.status >= 9) {
                    startActivity(new Intent(Volunteer.this,NotificationActivity.class));
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Volunteer.this).create();
                    alertDialog.setTitle("Unauthorized");
                    alertDialog.setMessage("You are not an admin");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                        Toast.makeText(getApplicationContext(),"Login as Events Volunteer",Toast.LENGTH_SHORT).show();
                                }
                            });
                    alertDialog.show();
                }

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Volunteer.this, Login.class));

            }
        });
    }
}
