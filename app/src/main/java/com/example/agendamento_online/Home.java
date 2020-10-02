package com.example.agendamento_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agendamento_online.Paciente.Consulta;
import com.example.agendamento_online.authentication.Conexao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;

    TextView view_nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        view_nome = (TextView) findViewById(R.id.view_nome);
        Button logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conexao.logOut();

                Intent voltar = new Intent(Home.this, MainActivity.class);
                startActivity(voltar);
            }
        });

        Button agendarConsulta = (Button) findViewById(R.id.cadastrar_consulta);

        agendarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agendamento = new Intent(Home.this, Consulta.class);
                startActivity(agendamento);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        database = FirebaseDatabase.getInstance();

        verificarUser();
    }


    private void verificarUser() {

        if (user == null) {
            Intent principal = new Intent(Home.this, MainActivity.class);
            startActivity(principal);
        } else {
            DatabaseReference reference = database.getReference().child("Paciente").child(user.getUid());

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String nome = snapshot.child("nome").getValue(String.class);
                    view_nome.setText(nome);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}