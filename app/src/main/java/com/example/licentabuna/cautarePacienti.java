package com.example.licentabuna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class cautarePacienti extends AppCompatActivity {
    public static EditText resultsearchview;
    private FirebaseAuth FirebaseAuth;
    ImageButton scantosearch;
    Button searchbtn;
    Adapter adapter;
    RecyclerView mrecyclerview;
    DatabaseReference mdatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cautare_pacienti);

        FirebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser users=FirebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail=finaluser.replace(".","");
        mdatabaseref= FirebaseDatabase.getInstance().getReference("silviuzaharie11@gmailcom/Pacienti/");

        resultsearchview=findViewById(R.id.searchfield);
        scantosearch=findViewById(R.id.scanButtonsearch);
        searchbtn=findViewById(R.id.searchBtn);


        LinearLayoutManager manager=new LinearLayoutManager(this);

        mrecyclerview=(RecyclerView) findViewById(R.id.List);
        mrecyclerview.setHasFixedSize(true);

        mrecyclerview.setLayoutManager(manager);

        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));



        scantosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),cititor_QR.class));
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext=resultsearchview.getText().toString();
                firebasesearch(searchtext);
            }
        });
    }
    public void firebasesearch(String searchtext){


        FirebaseRecyclerOptions<Pacienti>options=
                new FirebaseRecyclerOptions.Builder<Pacienti>()
                        .setLifecycleOwner(this)
                .setQuery(mdatabaseref,Pacienti.class)
                .build();


        Query firebaseSearchQuery=mdatabaseref.orderByChild("qrCode").startAt(searchtext).endAt(searchtext+"\uf8ff");

        FirebaseRecyclerAdapter<Pacienti,UsersViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Pacienti, UsersViewHolder>
                        (options)
                    //Pacienti.class,R.layout.pacienti_layout,UsersViewHolder.class,firebaseSearchQuery
                {


            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder viewHolder, int position, @NonNull Pacienti model) {
                viewHolder.setDetails(getApplicationContext(),model.getQrcode(),model.getNumeP(),model.getPrenP(),model.getCnpP(),model.getSimpP());
            }


            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pacienti_layout,parent,false);

                return new UsersViewHolder(view);

            }

        };
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);

    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UsersViewHolder(View itemView){
            super(itemView);
            mView=itemView;
        }
        public void setDetails(Context ctx, String pacientbarcode, String pacientid, String pacientname, String pacientpren, String pacientcnp ) {
            TextView pacient_id = (TextView) mView.findViewById(R.id.idPacient);
            TextView pacient_barcode = (TextView) mView.findViewById(R.id.qrPacient);
            TextView pacient_name = (TextView) mView.findViewById(R.id.numePacient);
            TextView pacient_pren = (TextView) mView.findViewById(R.id.prenumePacient);
            TextView pacient_cnp = (TextView) mView.findViewById(R.id.cnpPacient);
            pacient_id.setText(pacientid);
            pacient_barcode.setText(pacientbarcode);
            pacient_name.setText(pacientname);
            pacient_pren.setText(pacientpren);
            pacient_cnp.setText(pacientcnp);
        }
    }
}