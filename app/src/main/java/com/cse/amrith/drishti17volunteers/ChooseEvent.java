package com.cse.amrith.drishti17volunteers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.cse.amrith.drishti17volunteers.Models.EventModel;
import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.RestApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class ChooseEvent extends AppCompatActivity {
    HashMap<String,Integer> searchList;
    AutoCompleteTextView events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_event);
        events=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView) ;
        RestApiInterface service = ApiClient.getService();
        Call<List<EventModel>> call=service.getEvents();
        call.enqueue(new retrofit2.Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, retrofit2.Response<List<EventModel>> response) {
//                ArrayList<EventModel> cList= (ArrayList<EventModel>) response.body();
//                ArrayList<String> cnames=new ArrayList<String>();
//                searchList=new HashMap<String, Integer>();
//                for(EventModel c: cList){
//                    cnames.add(c.name);
//                    searchList.put(c.name,c.id);
//                }
//                ArrayAdapter<String> list=new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown,cnames);
//                events.setDropDownBackgroundResource(R.color.colorPrimary);
//                events.setAdapter(list);
//                events.setThreshold(0);
            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
            }
        });
    }
}
