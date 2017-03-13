package com.cse.amrith.drishti17volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cse.amrith.drishti17volunteers.Utils.QR;

public class Registration extends AppCompatActivity {
    Button qr;
    TextView UID;
    int uid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
        qr=(Button)findViewById(R.id.qr);
        UID=(TextView) findViewById(R.id.uid_text);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Registration.this,QR.class);
                intent.putExtra("Volunteer","reg");
                startActivity(intent);
            }
        });
        if(getIntent()!=null)
        {
             uid=getIntent().getIntExtra("UID",0);
             UID.setText(String.valueOf(uid));
        }
    }
}
