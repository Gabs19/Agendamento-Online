package com.example.agendamento_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agendamento_online.Paciente.Cadastro_Consulta;
import com.example.agendamento_online.authentication.Conexao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    TextView view_nome,view_consulta;
    ListView list_consulta;

    private List<String> consultas = new ArrayList<String>();
    private ArrayAdapter<String> consultaArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        view_nome = (TextView) findViewById(R.id.view_nome);
        view_consulta = (TextView) findViewById(R.id.view_consulta);
        list_consulta = (ListView) findViewById(R.id.list_consulta);

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
                Intent agendamento = new Intent(Home.this, Cadastro_Consulta.class);
                startActivity(agendamento);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

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


            DatabaseReference consultaReferente = database.getReference().child("Paciente").child(user.getUid()).child("Consulta");

            consultaReferente.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    consultas.clear();
                    for (DataSnapshot obj : snapshot.getChildren()) {
                        String status = obj.child("status").getValue(String.class);
                        if (status.equals("Ativo")) {
                            String nome = obj.child("nomePaciente").getValue(String.class);
                            consultas.add(nome);
                        }
                    }
                    consultaArrayAdapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1,consultas);
                    list_consulta.setAdapter(consultaArrayAdapter);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }
    }

}