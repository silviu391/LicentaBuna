package com.example.licentabuna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class cititor_QR extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView resultData,data2;
    Pacienti pacient;

    int MY_PERMISSIONS_REQUEST_CAMERA=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cititor__q_r);
        scannerView=findViewById(R.id.scannerViewer);
        codeScanner=new CodeScanner(this,scannerView);
        resultData=findViewById(R.id.qrCodeRead);
        data2=findViewById(R.id.qrCode2R);



        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    /*public void run() {
                        resultData.setText(result.getText());
                    }*/
                    public void run(){
                        data2.setText(result.getText());
                        System.out.println(result);
                        addPacienti.resulttextview.setText(result.getText());
                    }

                });
            }

        });


        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { codeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
    @Override
    protected void onPostResume(){
        super.onPostResume();
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);
        }
        codeScanner.startPreview();
    }
}