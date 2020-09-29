package com.example.agendamento_online.Paciente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.agendamento_online.Home;
import com.example.agendamento_online.Model.Paciente;
import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dados_Cadastrais extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;

    EditText nome, sobrenome, cpf,celular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados__cadastrais);

        inicializarFirebase();

        nome =  findViewById(R.id.nome);
        sobrenome =  findViewById(R.id.sobrenome);
        cpf = findViewById(R.id.cpf);
        celular = findViewById(R.id.celular);

        Button inserir = (Button) findViewById(R.id.inserir);

        inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarDados();
                Intent perfil = new Intent(Dados_Cadastrais.this, Home.class);
                startActivity(perfil);
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Dados_Cadastrais.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user =  Conexao.getFirebaseUser();
    }

    private void cadastrarDados() {
        Paciente paciente = new Paciente();

        paciente.setId(user.getUid());
        paciente.setNome(nome.getText().toString());
        paciente.setSobrenome(sobrenome.getText().toString());
        paciente.setCpf(cpf.getText().toString()); //atributo de paciente
        paciente.setCelular(celular.getText().toString());
        paciente.setEmail(user.getEmail());

        databaseReference.child("Paciente").child(paciente.getId()).setValue(paciente);
    }
}