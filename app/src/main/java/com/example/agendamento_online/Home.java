package com.example.agendamento_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agendamento_online.Model.Consulta;
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

    TextView view_nome;
    ListView list_consulta;

    private List<Consulta> consultas = new ArrayList<Consulta>();
    private ArrayAdapter<Consulta> consultaArrayAdapter;

    Consulta consultaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        view_nome = (TextView) findViewById(R.id.view_nome);
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

        list_consulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                consultaSelecionada = (Consulta)parent.getItemAtPosition(position);
                String nome = consultaSelecionada.getNomePaciente();
                String data = consultaSelecionada.getData();
                String horario = consultaSelecionada.getHorario();

                read(nome,data,horario);

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
            //mostra o nome do usuario Logado
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

            //mostra as consultas cadastrada desse usuario
            DatabaseReference consultaReferente = database.getReference().child("Paciente").child(user.getUid()).child("Consulta");

            consultaReferente.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    consultas.clear();
                    for (DataSnapshot obj : snapshot.getChildren()) {
                        String status = obj.child("status").getValue(String.class);
                        if (status.equals("Ativo")) {
                            Consulta consulta = obj.getValue(Consulta.class);

                            consultas.add(consulta);
                        }
                    }
                    consultaArrayAdapter = new ArrayAdapter<Consulta>(Home.this, android.R.layout.simple_list_item_1,consultas);
                    list_consulta.setAdapter(consultaArrayAdapter);


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }
    }

    private void read(String nome, String data, String horario) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setTitle("Consulta");
        builder.setMessage("Nome do paciente: " + nome + "\nData da consulta : " + data + "\nHorario da consulta : " + horario);
        builder.create();
        builder.show();
    }

}