package com.cse.amrith.drishti17volunteers;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Models.EventAdmin;
import com.cse.amrith.drishti17volunteers.Models.EventAdmin;
import com.cse.amrith.drishti17volunteers.Models.EventAdminStudentModel;
import com.cse.amrith.drishti17volunteers.Models.EventModel;
import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;
import com.cse.amrith.drishti17volunteers.Utils.NetworkUtil;
import com.cse.amrith.drishti17volunteers.Utils.RestApiInterface;
import com.cse.amrith.drishti17volunteers.adapters.EventListAdapter;
import com.cse.amrith.drishti17volunteers.adapters.StudentListAdapter;
import com.cse.amrith.drishti17volunteers.adapters.StudentListExpandableAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventCoordinator extends AppCompatActivity {
    ExpandableListView lv;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_coordinator);
        lv=(ExpandableListView)findViewById(R.id.eventCoordinatorList);
        final HashMap<String,Integer> searchList=new HashMap<>();
        final AutoCompleteTextView event=(AutoCompleteTextView)findViewById(R.id.event);
        event.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getStudentList(searchList.get(event.getText().toString()));
            }
        });
        progressDialog=new ProgressDialog(EventCoordinator.this);
        if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            RestApiInterface service=ApiClient.getService();
            progressDialog.show();
            service.getEvents().enqueue(new Callback<List<EventModel>>() {
                @Override
                public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                    progressDialog.dismiss();
                    List<EventModel> events=response.body();
                    ArrayList<String> eventList=new ArrayList<String>();
                    for(EventModel e:events) {
                        eventList.add(e.name);
                        searchList.put(e.name,e.server_id);
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(EventCoordinator.this,android.R.layout.simple_list_item_1,eventList);
                    event.setAdapter(arrayAdapter);
                    event.setThreshold(0);
                }

                @Override
                public void onFailure(Call<List<EventModel>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Network Unavailable", Toast.LENGTH_SHORT);
        }
    }
    private void getStudentList(final int eventId){
        progressDialog.show();
        AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                RestApiInterface service = ApiClient.getService();
                Call<EventAdmin> call = service.getStudentList(token,eventId);
                call.enqueue(new Callback<EventAdmin>() {
                    @Override
                    public void onResponse(Call<EventAdmin> call, Response<EventAdmin> response) {
                        progressDialog.dismiss();
                        if (response.code() == 200) {
                            EventAdmin EventAdmin= (EventAdmin)response.body();
                            Log.i("got events", new Gson().toJson(EventAdmin));
                            lv.setAdapter(new StudentListExpandableAdapter(EventCoordinator.this, (ArrayList<EventAdminStudentModel>) EventAdmin.students));
                        } else {
                            Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<EventAdmin> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("ERROR", t.toString());
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}
