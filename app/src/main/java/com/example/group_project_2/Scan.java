package com.example.group_project_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class Scan extends AppCompatActivity {

    SurfaceView sv;
    BarcodeDetector bcd;
    CameraSource cs;
    int rpid = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        sv = (SurfaceView)findViewById(R.id.surfaceView);

        bcd = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cs = new CameraSource
                .Builder(this, bcd)
                .setRequestedPreviewSize(640, 480)
                .build();
    }
}
