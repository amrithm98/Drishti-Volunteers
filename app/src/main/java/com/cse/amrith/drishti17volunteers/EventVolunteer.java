package com.cse.amrith.drishti17volunteers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Models.RegisteredEvents;
import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;
import com.cse.amrith.drishti17volunteers.Utils.NetworkUtil;
import com.cse.amrith.drishti17volunteers.Utils.RestApiInterface;
import com.cse.amrith.drishti17volunteers.adapters.EventListAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventVolunteer extends AppCompatActivity {
    Button qr;
    TextView name;
    ListView events;
    String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_volunteer);
        events = (ListView) findViewById(R.id.eventList);
        name = (TextView) findViewById(R.id.tv_name_event);
        if (getIntent().getStringExtra("UID") != null) {
            uid = getIntent().getStringExtra("UID");
            if (uid != "") {
                if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                    AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
                        @Override
                        public void tokenObtained(String token) {
                            RestApiInterface service = ApiClient.getService();
                            Call<List<RegisteredEvents>> call = service.eventStatus(token, uid);
                            call.enqueue(new Callback<List<RegisteredEvents>>() {
                                @Override
                                public void onResponse(Call<List<RegisteredEvents>> call, Response<List<RegisteredEvents>> response) {
                                    if (response.code() == 200) {
                                        List<RegisteredEvents> registeredEvents = (List<RegisteredEvents>) response.body();
                                        EventListAdapter adapter = new EventListAdapter(registeredEvents, EventVolunteer.this, Long.valueOf(uid));
                                        events.setAdapter(adapter);
                                    } else {
                                        try {
                                            Log.i("fail",response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getApplicationContext(), "Unsuccessful Request", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<RegisteredEvents>> call, Throwable t) {
                                    Log.d("ERROR", t.toString());
                                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Network Unavailable", Toast.LENGTH_SHORT);

                }
            }
        }
    }
}

