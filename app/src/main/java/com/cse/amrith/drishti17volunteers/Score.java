package com.cse.amrith.drishti17volunteers;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.Models.Student;
import com.cse.amrith.drishti17volunteers.Utils.ApiClient;
import com.cse.amrith.drishti17volunteers.Utils.AuthUtil;
import com.cse.amrith.drishti17volunteers.Utils.NetworkUtil;
import com.cse.amrith.drishti17volunteers.Utils.RestApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Score extends AppCompatActivity {
    Button update;
    EditText score,reason;
    int uid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        update=(Button)findViewById(R.id.updateScore);
        score=(EditText)findViewById(R.id.score_value);
        reason=(EditText)findViewById(R.id.reason);
        if(getIntent()!=null)
        {
            uid=getIntent().getIntExtra("UID",0);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                        AuthUtil.getFirebaseToken(new AuthUtil.Listener() {
                            @Override
                            public void tokenObtained(String token) {
                                if(score.getText()!=null && reason.getText()!=null)
                                {
                                    RestApiInterface service = ApiClient.getService();
                                    int addScore=Integer.parseInt(score.getText().toString());
                                    String scoreReason=reason.getText().toString();
                                    Call<String> call = service.addScore(token,uid,addScore,scoreReason);
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(getApplicationContext(), "Score Updated", Toast.LENGTH_SHORT).show();
                                                String s = response.body();
                                                Log.d("Score",s);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Log.d("ERROR", t.toString());
                                            Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Network Unavailable", Toast.LENGTH_SHORT);
                    }
                }
            });
        }

    }
}
