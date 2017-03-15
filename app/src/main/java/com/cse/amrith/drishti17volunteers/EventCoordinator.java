package com.cse.amrith.drishti17volunteers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Models.EventAdmin;
import com.cse.amrith.drishti17volunteers.Models.EventAdmin;
import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;
import com.cse.amrith.drishti17volunteers.Utils.NetworkUtil;
import com.cse.amrith.drishti17volunteers.Utils.RestApiInterface;
import com.cse.amrith.drishti17volunteers.adapters.EventListAdapter;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventCoordinator extends AppCompatActivity {
    ListView lv;
    int eventId=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_coordinator);
        lv=(ListView)findViewById(R.id.eventCoordinatorList);
        if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
                @Override
                public void tokenObtained(String token) {
                    RestApiInterface service = ApiClient.getService();
                    Call<EventAdmin> call = service.getStudentList(token,eventId);
                    call.enqueue(new Callback<EventAdmin>() {
                        @Override
                        public void onResponse(Call<EventAdmin> call, Response<EventAdmin> response) {
                            if (response.code() == 200) {
                                EventAdmin EventAdmin= (EventAdmin)response.body();
                                Log.i("got events", new Gson().toJson(EventAdmin));
                            } else {
                                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<EventAdmin> call, Throwable t) {
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
