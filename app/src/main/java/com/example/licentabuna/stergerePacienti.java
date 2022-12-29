package com.example.licentabuna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class stergerePacienti extends AppCompatActivity {
    public static EditText resultdelview;
    private FirebaseAuth firebaseAuth;
    Button scantodel,deletebtn;
    DatabaseReference databaseReference;
    cititor_QR citior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stergere_pacienti);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("silviuzaharie11@gmailcom/Pacienti/");

        resultdelview=findViewById(R.id.stergereCodQR);
        scantodel=findViewById(R.id.scantodelete);
        deletebtn=findViewById(R.id.DeleteitemFromDB);

        scantodel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),cititor_QR.class));
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletefrmdb();
            }
        });
    }

    public void deletefrmdb()
    {
        String deleteqrcodeval=resultdelview.getText().toString();
        final FirebaseUser users=firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail=finaluser.replace(".","");

        if(!TextUtils.isEmpty(deleteqrcodeval)){
            databaseReference.removeValue();//de unde se face stergerea
            Toast.makeText(stergerePacienti.this,"Pacientul a fost sters",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(stergerePacienti.this,"Va rugam scanati codul QR",Toast.LENGTH_SHORT).show();
        }
    }
}