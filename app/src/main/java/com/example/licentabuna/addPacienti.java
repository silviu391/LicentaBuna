package com.example.licentabuna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addPacienti extends AppCompatActivity {

    private EditText numeP,prenP,cnpP,ageP,simpP,idP;
    private TextView qrcode;
    private FirebaseAuth firebaseAuth;
    public static TextView resulttextview;
    Button scanbutton,additemtodb;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;
    private RadioGroup group;
    private RadioButton rbGender;

    cititor_QR cititor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pacienti);
        firebaseAuth=FirebaseAuth.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("");//adaugare copil pe arbore
      //  databaseReferencecat=FirebaseDatabase.getInstance().getReference();//-=||=-

        resulttextview=findViewById(R.id.textGeneratID);
       additemtodb=findViewById(R.id.adaugarePacient);
       scanbutton=findViewById(R.id.buttonScan);
       numeP=findViewById(R.id.editNume);
        prenP=findViewById(R.id.editPrenume);
        cnpP=findViewById(R.id.editCNP);
        ageP=findViewById(R.id.editAge);
        simpP=findViewById(R.id.editSimp);
        qrcode=findViewById(R.id.textID);



        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),cititor_QR.class);
                startActivity(i);
            }
        });

        additemtodb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem();
            }
        });
        





        //radio group
        group=findViewById(R.id.groupRb);
        int gender=group.getCheckedRadioButtonId();
        rbGender=findViewById(gender);
        if(rbGender != null){
            Toast.makeText(this, "Nu ati selectat sex-ul persoanei" + rbGender.getText(),Toast.LENGTH_SHORT).show();
        }

    }
    public void additem() {
        String numePValue = numeP.getText().toString();
        String prenPValue = prenP.getText().toString();
        String cnpPValue = cnpP.getText().toString();
        String agePValue = ageP.getText().toString();
        String simpPValue = simpP.getText().toString();





        String qrCodeValue = resulttextview.getText().toString();


        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String finaluser = user.getEmail();
        String resultemail = finaluser.replace(".", "");
        if (qrCodeValue.isEmpty()) {
            resulttextview.setError("E null");
            resulttextview.requestFocus();
            return;
        }

        if (!TextUtils.isEmpty(numePValue) && !TextUtils.isEmpty(prenPValue) && !TextUtils.isEmpty(cnpPValue) && !TextUtils.isEmpty(agePValue) && !TextUtils.isEmpty(qrCodeValue) && !TextUtils.isEmpty(simpPValue)) {

            Pacienti pacienti = new Pacienti(numePValue, prenPValue, cnpPValue, agePValue, simpPValue, qrCodeValue);
            databaseReference.child(resultemail).child("Pacienti").child(qrCodeValue).setValue(pacienti);
/*
            databaseReferencecat.child(resultemail).child("ID").child(simpPValue).child(qrCodeValue).setValue(pacienti);



*/


            numeP.setText("");
            qrcode.setText("");
            prenP.setText("");
            cnpP.setText("");
            ageP.setText("");
            simpP.setText("");
            resulttextview.setText("");
            Toast.makeText(this, numePValue + "Adaugat", Toast.LENGTH_SHORT).show();
        }
            else{
                Toast.makeText(this, "Va rog completati toate campurile", Toast.LENGTH_SHORT).show();
            }

    }

//        private void Logout() {
//        firebaseAuth.signOut();
//        finish();
//        startActivity(new Intent(this,Login.class));
//            Toast.makeText(this, "Logout reusit", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.logotMenu:{
//                    Logout();
//            }
        }


