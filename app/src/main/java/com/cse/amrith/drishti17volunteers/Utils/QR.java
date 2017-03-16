package com.cse.amrith.drishti17volunteers.Utils;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.EventVolunteer;
import com.cse.amrith.drishti17volunteers.Global;
import com.cse.amrith.drishti17volunteers.Models.RegisteredEvents;
import com.cse.amrith.drishti17volunteers.Models.Student;
import com.cse.amrith.drishti17volunteers.R;
import com.cse.amrith.drishti17volunteers.Registration;
import com.cse.amrith.drishti17volunteers.Score;
import com.cse.amrith.drishti17volunteers.Volunteer;
import com.cse.amrith.drishti17volunteers.adapters.EventListAdapter;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QR extends AppCompatActivity {

    TextView barcodeInfo;
    SurfaceView cameraView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    EditText identifier;
    Button bCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        barcodeInfo = (TextView) findViewById(R.id.code_info);
        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();
        identifier = (EditText) findViewById(R.id.identifier);
        bCheck = (Button) findViewById(R.id.check);
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {
                        // Use the post method of the TextView
                        int count = 0;

                        public void run() {
                            cameraSource.stop();
                            final String uid = barcodes.valueAt(0).displayValue;
                            if (getIntent().getStringExtra("Volunteer") != null) {
                                String s = getIntent().getStringExtra("Volunteer");
                                if (s.equalsIgnoreCase("reg") && count == 0) {
                                    count += 1;
                                    Log.d("Intnt", getIntent().getStringExtra("Volunteer"));
                                    Intent intent = new Intent(QR.this, Registration.class);
                                    intent.putExtra("UID", uid);
                                    startActivity(intent);
                                    finish();
                                } else if (s.equalsIgnoreCase("event") && count == 0) {
                                    count += 1;
                                    Intent intent = new Intent(QR.this, EventVolunteer.class);
                                    intent.putExtra("UID", uid);
                                    startActivity(intent);
                                    finish();
                                } else if (s.equalsIgnoreCase("update") && count == 0) {
                                    LayoutInflater inflater = LayoutInflater.from(QR.this);
                                    final View alertView = inflater.inflate(R.layout.update_score_dialogue, null);
                                    new AlertDialog.Builder(QR.this).setView(alertView)
                                            .setTitle("Update Score")
                                            .setIcon(R.drawable.drishti_logo0)
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    final EditText score=(EditText)alertView.findViewById(R.id.score);
                                                    final EditText reason=(EditText)alertView.findViewById(R.id.reason);
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
                                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
//                                    Intent intent = new Intent(QR.this, Score.class);
//                                    intent.putExtra("UID", uid);
//                                    startActivity(intent);
//                                    finish();
                                }
                            }
                            Thread.currentThread().isInterrupted();
                        }
                    });
                }
            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");

        bCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                    progressDialog.show();
                    RestApiInterface service = ApiClient.getService();
                    Call<Student> call = service.studentDetailsPublic(identifier.getText().toString());
                    call.enqueue(new Callback<Student>() {
                        @Override
                        public void onResponse(Call<Student> call, Response<Student> response) {
                            progressDialog.dismiss();
                            if (response.code() == 200) {
                                Student student = response.body();
                                if (getIntent().getStringExtra("Volunteer") != null) {
                                    String s = getIntent().getStringExtra("Volunteer");
                                    final String uid = student.id;
                                    if (s.equalsIgnoreCase("reg")) {
                                        Log.d("Intnt", getIntent().getStringExtra("Volunteer"));
                                        Intent intent = new Intent(QR.this, Registration.class);
                                        intent.putExtra("UID", uid);
                                        startActivity(intent);
                                        finish();
                                    } else if (s.equalsIgnoreCase("event")) {
                                        Intent intent = new Intent(QR.this, EventVolunteer.class);
                                        intent.putExtra("UID", uid);
                                        startActivity(intent);
                                        finish();
                                    } else if (s.equalsIgnoreCase("update")) {
                                        LayoutInflater inflater = LayoutInflater.from(QR.this);
                                        final View alertView = inflater.inflate(R.layout.update_score_dialogue, null);
                                        new AlertDialog.Builder(QR.this).setView(alertView)
                                                .setTitle("Update Score")
                                                .setIcon(R.drawable.drishti_logo0)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        final EditText score=(EditText)alertView.findViewById(R.id.score);
                                                        final EditText reason=(EditText)alertView.findViewById(R.id.reason);
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
                                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No user", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Student> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.d("ERROR", t.toString());
                            Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Network Unavailable", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
