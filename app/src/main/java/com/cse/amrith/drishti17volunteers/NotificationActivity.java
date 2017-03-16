package com.cse.amrith.drishti17volunteers;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    EditText title,body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        title=(EditText)findViewById(R.id.title);
        body=(EditText)findViewById(R.id.message);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NotificationActivity.this).setTitle("Confirmation").setMessage("Are you sure you want to send this notification?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                send();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }
    public void send(){
        final ProgressDialog progressDialog=new ProgressDialog(NotificationActivity.this);
        progressDialog.show();
        AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                ApiClient.getService().sendNotif(token,title.getText().toString(),body.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        progressDialog.dismiss();
                        if(response.code()==200)
                            Toast.makeText(NotificationActivity.this,"Sent",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(NotificationActivity.this,"Failed",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(NotificationActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
