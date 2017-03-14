package com.cse.amrith.drishti17volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Models.RegisteredEvents;
import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;
import com.cse.amrith.drishti17volunteers.Utils.NetworkUtil;
import com.cse.amrith.drishti17volunteers.Utils.QR;
import com.cse.amrith.drishti17volunteers.Utils.RestApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventVolunteer extends AppCompatActivity {
    Button qr,regEvents;
    TextView uid_text;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_volunteer);
        regEvents=(Button)findViewById(R.id.events);
        uid_text=(TextView)findViewById(R.id.uid_text);
        if(getIntent().getIntExtra("UID",0)!=0) {
            uid = getIntent().getIntExtra("UID", 0);
            uid_text.setText(String.valueOf(uid));
        }
        regEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                    AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
                        @Override
                        public void tokenObtained(String token) {
                            RestApiInterface service = ApiClient.getService();
                            Call<List<RegisteredEvents>> call = service.eventStatus(token,uid);
                            call.enqueue(new Callback<List<RegisteredEvents>>() {
                                @Override
                                public void onResponse(Call<List<RegisteredEvents>>  call, Response<List<RegisteredEvents>> response) {
                                    if (response.code() == 200) {
                                        ArrayList<RegisteredEvents> registeredEvents = (ArrayList<RegisteredEvents>) response.body();
                                        String s="";
                                        for(RegisteredEvents reg: registeredEvents)
                                        {
                                            Log.d("ARRAY",reg.paid.toString());
                                            s+=reg.name;
                                        }
                                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<RegisteredEvents>>  call, Throwable t) {
                                    Log.d("ERROR",t.toString());
                                    Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(),"Network Unavailable",Toast.LENGTH_SHORT);

                }
            }
        });
    }
}
