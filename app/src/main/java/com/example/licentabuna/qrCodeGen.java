package com.example.licentabuna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class qrCodeGen extends AppCompatActivity {
    EditText teInput;
    Button btGen;
    ImageView ivOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_gen);


        teInput=findViewById(R.id.inputQR);
        btGen=findViewById(R.id.generatorQR);
        ivOut=findViewById(R.id.imagView_out);

        btGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sText=teInput.getText().toString().trim();
                MultiFormatWriter writer=new MultiFormatWriter();
                try {
                    BitMatrix matrix=writer.encode(sText, BarcodeFormat.QR_CODE,250,250);
                    BarcodeEncoder encoder=new BarcodeEncoder();
                    Bitmap bitmap=encoder.createBitmap(matrix);
                    ivOut.setImageBitmap(bitmap);
                    InputMethodManager manger=(InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );
                    manger.hideSoftInputFromWindow(teInput.getApplicationWindowToken(),0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}