package com.example.licentabuna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainFrame extends AppCompatActivity  implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    TextView firebasenameview;
    Button toast;

    private CardView addPacienti,searchPacienti,transfPacienti,delPacienti,creare_card,logoutPacient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);
        firebasenameview = findViewById(R.id.firebase_nume); //nume firebase

        firebaseAuth=FirebaseAuth.getInstance();

        final FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user !=null) {
            String finaluser = (user).getEmail();
            String result = finaluser.substring(0, finaluser.indexOf("@"));
            String resultemail = result.replace(".", "");
            firebasenameview.setText("" + resultemail);
        }
  /*      toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainFrame.this,users.getEmail(),Toast.LENGTH_SHORT).show();
           }
        });*/

        //Creare evenimente pe cardview
        addPacienti=(CardView)findViewById(R.id.add_pacient);
        searchPacienti=(CardView)findViewById(R.id.cautare_pacient);
        transfPacienti=(CardView)findViewById(R.id.transf_pacient);
        creare_card=(CardView)findViewById(R.id.generareCard);
        delPacienti=(CardView)findViewById(R.id.stergere_pacient);
        logoutPacient=(CardView)findViewById(R.id.logout_pacient);


        //apasare de click
        addPacienti.setOnClickListener(this);
        searchPacienti.setOnClickListener(this);
        transfPacienti.setOnClickListener(this);
        creare_card.setOnClickListener(this);
        delPacienti.setOnClickListener(this);
        logoutPacient.setOnClickListener(this);


    }

    @Override
    public void onClick(View view){
        Intent i;

        //schimbare activitate in functie de click
        switch (view.getId()){
            case R.id.add_pacient:i=new Intent(this,addPacienti.class);
              startActivity(i);
              break;
            case R.id.cautare_pacient:i=new Intent(this,cautarePacienti.class);
             startActivity(i);
             break;
            case R.id.transf_pacient:i=new Intent(this,updatePacient.class);
            startActivity(i);
            break;
            case R.id.generareCard:i=new Intent(this,creare_card.class);
            startActivity(i);
            break;
            case R.id.stergere_pacient:i=new Intent(this,stergerePacienti.class);
                startActivity(i);
                break;
            case R.id.logout_pacient:
                firebaseAuth.getInstance().signOut();
                i=new Intent(this,Login.class);
                startActivity(i);
                break;


            default:break;
            }

    }
}