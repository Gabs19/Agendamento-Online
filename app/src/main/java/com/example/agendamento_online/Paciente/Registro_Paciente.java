package com.example.agendamento_online.Paciente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro_Paciente extends AppCompatActivity {


    FirebaseAuth auth;

    EditText email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__paciente);

         email =  findViewById(R.id.email);
         senha = findViewById(R.id.senha);

        inicializarFirebase();

        Button cadastrar = (Button) findViewById(R.id.cadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrando();
            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Registro_Paciente.this);
    }

    public void cadastrando() {

        String user_email = email.getText().toString().trim();
        String user_senha = senha.getText().toString().trim();

        criarUserPaciente(user_email, user_senha);
    }

    public void criarUserPaciente (String email, String senha) {

        auth.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(Registro_Paciente.this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           alert("Cadastrado com sucesso!");

                           Intent voltarInicio = new Intent(Registro_Paciente.this, Dados_Cadastrais.class);
                           startActivity(voltarInicio);
                       } else {
                            alert("Erro ao cadastrar");
                       }
                   }
                });

    }

    private void alert(String msg) {
        Toast.makeText(Registro_Paciente.this,msg, Toast.LENGTH_SHORT).show();
    }

    protected  void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

}