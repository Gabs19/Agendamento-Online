package com.example.agendamento_online.Paciente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendamento_online.Home;
import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Paciente extends AppCompatActivity {

    EditText email, senha;
    TextView reset;
    Button logar;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.loign_email);
        senha = (EditText) findViewById(R.id.login_pass);

        logar = (Button) findViewById(R.id.entrar);

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String mail = email.getText().toString().trim();
               String password = senha.getText().toString().trim();
               login(mail, password);
            }
        });

        reset = (TextView) findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetar = new Intent(Login_Paciente.this, Reset_senha.class);
                startActivity(resetar);
            }
        });
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Login_Paciente.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent entrar = new Intent(Login_Paciente.this, Home.class);
                            startActivity(entrar);
                        } else {
                            alert("Dados de usuario errados");
                        }
                    }
                });
    }

    private void alert(String msg) {
        Toast.makeText(Login_Paciente.this,msg,Toast.LENGTH_SHORT).show();
    }

    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}