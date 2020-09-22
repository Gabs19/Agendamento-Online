package com.example.agendamento_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.agendamento_online.Model.Medico;
import com.example.agendamento_online.Model.Paciente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Registro_Paciente extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText nome, sobrenome, cpf,celular, email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__paciente);

         nome =  findViewById(R.id.nome);
         sobrenome =  findViewById(R.id.sobrenome);
         cpf = findViewById(R.id.cpf);
         celular = findViewById(R.id.celular);
         email =  findViewById(R.id.email);
         senha = findViewById(R.id.senha);

        inicializarFirebase();

        Button cadastrar = (Button) findViewById(R.id.cadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrando();

                Intent voltarInicio = new Intent(Registro_Paciente.this, MainActivity.class);
                startActivity(voltarInicio);
            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Registro_Paciente.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void cadastrando() {
        Paciente paciente = new Paciente();

        paciente.setId(UUID.randomUUID().toString());
        paciente.setNome(nome.getText().toString());
        paciente.setSobrenome(sobrenome.getText().toString());
        paciente.setCpf(cpf.getText().toString()); //atributo de paciente
        paciente.setCelular(celular.getText().toString());
        paciente.setEmail(email.getText().toString());
        paciente.setSenha(senha.getText().toString());

        databaseReference.child("Paciente").child(paciente.getId()).setValue(paciente);

    }


}