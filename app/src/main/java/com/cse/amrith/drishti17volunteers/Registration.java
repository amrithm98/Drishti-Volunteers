package com.cse.amrith.drishti17volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Models.RegisteredEvents;
import com.cse.amrith.drishti17volunteers.Models.Student;
import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;
import com.cse.amrith.drishti17volunteers.Utils.NetworkUtil;
import com.cse.amrith.drishti17volunteers.Utils.QR;
import com.cse.amrith.drishti17volunteers.Utils.RestApiInterface;
import com.cse.amrith.drishti17volunteers.adapters.EventListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    Button qr,regEvents;
    ListView events;
    int uid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_registation);
        regEvents=(Button)findViewById(R.id.regEvents);
        events=(ListView)findViewById(R.id.lv_events);
        if(getIntent()!=null)
        {
             uid=getIntent().getIntExtra("UID",0);
             if(uid!=0) {
                 if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                     AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
                         @Override
                         public void tokenObtained(String token) {
                             RestApiInterface service = ApiClient.getService();
                             Call<Student> call = service.studentDetails(token,uid);
                             call.enqueue(new Callback<Student>() {
                                 @Override
                                 public void onResponse(Call<Student> call, Response<Student> response) {
                                     if (response.code() == 200) {
                                         Toast.makeText(getApplicationContext(), "Student Details Fetched", Toast.LENGTH_SHORT).show();
                                         Student stud = response.body();
                                         Log.d("Student", stud.toString());
                                     } else {
                                         Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                                 @Override
                                 public void onFailure(Call<Student> call, Throwable t) {
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
        if(NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
                @Override
                public void tokenObtained(String token) {
                    RestApiInterface service = ApiClient.getService();
                    Call<List<RegisteredEvents>> call = service.eventStatus(token,uid);
                    call.enqueue(new Callback<List<RegisteredEvents>> () {
                        @Override
                        public void onResponse(Call<List<RegisteredEvents>>  call, Response<List<RegisteredEvents>>  response) {
                            if (response.code() == 200) {
                                List<RegisteredEvents> registeredEvents = (List<RegisteredEvents>) response.body();
                                EventListAdapter adapter=new EventListAdapter(registeredEvents,getApplicationContext());
                                events.setAdapter(adapter);
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
}
