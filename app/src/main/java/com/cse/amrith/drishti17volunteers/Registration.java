package com.cse.amrith.drishti17volunteers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Models.PaymentModel;
import com.cse.amrith.drishti17volunteers.Models.RegisteredEvents;
import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;
import com.cse.amrith.drishti17volunteers.Utils.NetworkUtil;
import com.cse.amrith.drishti17volunteers.Utils.RestApiInterface;
import com.cse.amrith.drishti17volunteers.adapters.EventListAdapter;
import com.google.gson.Gson;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements EventListAdapter.EventListListener {


    Button qr, payment;
    TextView name;
    ListView events;
    String uid = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_registation);
        name = (TextView) findViewById(R.id.tv_name);
        events = (ListView) findViewById(R.id.lv_events);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        if (getIntent() != null) {
            uid = getIntent().getStringExtra("UID");
            name = (TextView) findViewById(R.id.tv_name);
            events = (ListView) findViewById(R.id.lv_events);
            if (getIntent() != null) {
                uid = getIntent().getStringExtra("UID");
                if (uid != "") {
                    //name.setText(Global.student.name);
                    if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                        progressDialog.show();
                        RestApiInterface service = ApiClient.getService();
                        Call<List<RegisteredEvents>> call = service.eventStatus(uid);
                        call.enqueue(new Callback<List<RegisteredEvents>>() {
                            @Override
                            public void onResponse(Call<List<RegisteredEvents>> call, Response<List<RegisteredEvents>> response) {
                                progressDialog.dismiss();
                                if (response.code() == 200) {
                                    List<RegisteredEvents> registeredEvents = (List<RegisteredEvents>) response.body();
                                    Log.i("got events", new Gson().toJson(registeredEvents));
                                    EventListAdapter adapter = new EventListAdapter(registeredEvents, Registration.this, Long.valueOf(uid));
                                    events.setAdapter(adapter);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<RegisteredEvents>> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.d("ERROR", t.toString());
                                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Network Unavailable", Toast.LENGTH_SHORT);
                    }
                }
            }

//            payment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    EventListAdapter adapter = (EventListAdapter) events.getAdapter();
//                    List<RegisteredEvents> finalList = adapter.returnList();
//                    final PaymentModel[] objects = new PaymentModel[adapter.getCount()];
//                    int i = 0;
//                    String s = "";
//                    for (RegisteredEvents reg : finalList) {
//                        objects[i] = new PaymentModel();
//                        objects[i].eventId = reg.id;
//                        objects[i].paid = reg.paid;
//                        s += String.valueOf(objects[i].eventId) + " " + String.valueOf(objects[i].paid + "X");
//                        i = i + 1;
//                    }
//                    if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
//                        AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
//                            @Override
//                            public void tokenObtained(String token) {
//                                RestApiInterface service = ApiClient.getService();
//                                Call<String> call = service.confirmPayment(token, uid, objects);
//                                call.enqueue(new Callback<String>() {
//                                    @Override
//                                    public void onResponse(Call<String> call, Response<String> response) {
//                                        if (response.code() == 200) {
//                                            Toast.makeText(getApplicationContext(), "Payment Updated", Toast.LENGTH_SHORT).show();
//                                            Log.d("Response",String.valueOf(response.body()));
//                                        } else {
//                                            Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<String> call, Throwable t) {
//                                        Log.d("ERROR", t.toString());
//                                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                        });
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Network Unavailable", Toast.LENGTH_SHORT);
//                    }
//                    //Log.d("TAG",s);
//                }
//            });
        }
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }
}
