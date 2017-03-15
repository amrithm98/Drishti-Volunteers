package com.cse.amrith.drishti17volunteers.Utils;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.amrith.drishti17volunteers.EventVolunteer;
import com.cse.amrith.drishti17volunteers.Global;
import com.cse.amrith.drishti17volunteers.Models.Student;
import com.cse.amrith.drishti17volunteers.R;
import com.cse.amrith.drishti17volunteers.Registration;
import com.cse.amrith.drishti17volunteers.Score;
import com.cse.amrith.drishti17volunteers.Volunteer;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QR extends AppCompatActivity {

    TextView barcodeInfo;
    SurfaceView cameraView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    EditText identifier;
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
        identifier=(EditText)findViewById(R.id.identifier);
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
                        int count=0;
                        public void run() {
                            cameraSource.stop();
                            final String uid=barcodes.valueAt(0).displayValue;
                            if(getIntent().getStringExtra("Volunteer")!=null) {
                                String s = getIntent().getStringExtra("Volunteer");
                                if (s.equalsIgnoreCase("reg") && count==0) {
                                    count+=1;
                                    Log.d("Intnt",getIntent().getStringExtra("Volunteer"));
                                    Intent intent = new Intent(QR.this, Registration.class);
                                    intent.putExtra("UID", uid);
                                    startActivity(intent);
                                    finish();
                                } else if (s.equalsIgnoreCase("event") && count==0) {
                                    count+=1;
                                    Intent intent = new Intent(QR.this, EventVolunteer.class);
                                    intent.putExtra("UID", uid);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(s.equalsIgnoreCase("update") && count==0)
                                {
                                    Intent intent = new Intent(QR.this,Score.class);
                                    intent.putExtra("UID", uid);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            Thread.currentThread().isInterrupted();
                        }
                    });
                }
            }
        });
    }
}
