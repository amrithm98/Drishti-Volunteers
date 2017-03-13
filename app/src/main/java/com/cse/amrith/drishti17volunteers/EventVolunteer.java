package com.cse.amrith.drishti17volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cse.amrith.drishti17volunteers.Utils.QR;

public class EventVolunteer extends AppCompatActivity {
    Button qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_volunteer);
        qr=(Button)findViewById(R.id.qr);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventVolunteer.this,QR.class);
                intent.putExtra("Volunteer","event");
                startActivity(intent);
            }
        });
    }
}
