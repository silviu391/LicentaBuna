package com.example.licentabuna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class updatePacient extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    Button btnUpdate;
    EditText textNume,textPrenume,textCnp,textSectie,textID;
    Pacienti pacient;
    DatabaseReference mdatabaseref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pacient);

        btnUpdate=findViewById(R.id.updateBtn);
        textNume=findViewById(R.id.updateNume);
        textPrenume=findViewById(R.id.updatePrenume);
        textCnp=findViewById(R.id.updateCNP);
        textSectie=findViewById(R.id.updateSectie);
        textID=findViewById(R.id.updateID);




        firebaseAuth=firebaseAuth.getInstance();
        final FirebaseUser users=firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail=finaluser.replace(".","");
        mdatabaseref= FirebaseDatabase.getInstance().getReference("silviuzaharie11@gmailcom/Pacienti");


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textNume.getText().toString().trim();
                String prenume = textPrenume.getText().toString().trim();
                String cnp = textCnp.getText().toString().trim();
                String sectie = textSectie.getText().toString().trim();
                String id = textID.getText().toString().trim();


                /*HashMap <String,Object> data = new HashMap<>();

                data.put("qrcode", textNume);*/


                mdatabaseref.child("ID/numeP").setValue(username);
                mdatabaseref.child("ID/prenP").setValue(prenume);
                mdatabaseref.child("ID/cnpP").setValue(cnp);
                mdatabaseref.child("ID/simpP").setValue(sectie);
                mdatabaseref.child("ID/qrcode").setValue(id);






                Toast.makeText(updatePacient.this, "Pacientul a fost actualizat", Toast.LENGTH_SHORT).show();


            }
        });

    }
}