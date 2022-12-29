package com.example.licentabuna;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class creare_card extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    EditText idInput;
    Button btCardGen;
    ImageView ivCard;
    DatabaseReference mdatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creare_card);

        firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser users=firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail=finaluser.replace(".","");
        mdatabaseref= FirebaseDatabase.getInstance().getReference("silviuzaharie11@gmailcom/Pacienti");

        idInput=findViewById(R.id.inputID);
        btCardGen=findViewById(R.id.generareCard);
        ivCard=findViewById(R.id.imagView_card);

        btCardGen.setOnClickListener(v -> {
            String sText=idInput.getText().toString().trim();
            MultiFormatWriter writer=new MultiFormatWriter();
            Bitmap bitmap = null;
            try {
                BitMatrix matrix=writer.encode(sText, BarcodeFormat.QR_CODE,250,250);
                BarcodeEncoder encoder=new BarcodeEncoder();
                bitmap=encoder.createBitmap(matrix);
                ivCard.setImageBitmap(bitmap);
                InputMethodManager manger=(InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE
                );
                manger.hideSoftInputFromWindow(idInput.getApplicationWindowToken(),0);
            } catch (WriterException e) {
                e.printStackTrace();
            }


            // DB QUERY

            Bitmap finalBitmap = bitmap;
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI

                    System.out.println(dataSnapshot.getKey());
                    Pacienti person = null;
                        for(DataSnapshot child : dataSnapshot.getChildren()) {

                            System.out.println(child.getValue()+"89");
                                     if (child.getKey().equals(sText)) {
                                        person=child.getValue(Pacienti.class);

                            }
                        }



                    //DESENEAZA CARDUL

                    Bitmap src = finalBitmap; // the original file yourimage.jpg i added in resources
                    Bitmap dest = Bitmap.createBitmap(src.getWidth()+230, src.getHeight()+50, Bitmap.Config.ARGB_8888);



                    Canvas cs = new Canvas(dest);
                    Paint tPaint = new Paint();
                    tPaint.setTextSize(25);
                    tPaint.setColor(Color.BLUE);
                    tPaint.setTypeface(Typeface.create("Roboto", Typeface.BOLD));
                    tPaint.setStyle(Paint.Style.FILL);
                    cs.drawBitmap(src, 250f, 25f, null);
                    float height = tPaint.measureText("yY");


                    cs.drawText(person.getNumeP(), 15, 100, tPaint);
                    cs.drawText(person.getPrenP(), 15, 145, tPaint);
                    cs.drawText(person.getCnpP(), 15, 190, tPaint);
                    cs.drawText(person.getQrcode(), 15, 225, tPaint);// 15f is to put space between top edge and the text, if you want to change it, you can


                    ivCard.setImageBitmap(dest);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    System.out.println("Eroare baza de date");
                }
            };
            mdatabaseref.orderByKey().addValueEventListener(postListener);
        });

    }
}