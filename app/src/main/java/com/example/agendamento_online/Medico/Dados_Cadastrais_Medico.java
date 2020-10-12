package com.example.agendamento_online.Medico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agendamento_online.Home;
import com.example.agendamento_online.Model.Medico;
import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dados_Cadastrais_Medico extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;

    EditText nome, sobrenome, celular, registro, especialidade;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados__cadastrais_medico);

        inicializarFirebase();

        nome = findViewById(R.id.nome);
        sobrenome = findViewById(R.id.sobrenome);
        celular = findViewById(R.id.celular);
        registro = findViewById(R.id.registro);
        especialidade = findViewById(R.id.especialidade);

        Button inserir = (Button) findViewById(R.id.inserir);

        inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarDados();
                Intent perfil = new Intent(Dados_Cadastrais_Medico.this, Home_medico.class);
                startActivity(perfil);
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Dados_Cadastrais_Medico.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user =  Conexao.getFirebaseUser();
    }

    private void cadastrarDados() {
        Medico medico = new Medico();

        medico.setId(user.getUid());
        medico.setNome(nome.getText().toString());
        medico.setSobrenome(sobrenome.getText().toString());
        medico.setRegistro(registro.getText().toString()); //atributo de medico
        medico.setCelular(celular.getText().toString());
        medico.setEmail(user.getEmail());
        medico.setEspecialidade(especialidade.getText().toString());

        databaseReference.child("Medico").child(medico.getId()).setValue(medico);
    }
}
