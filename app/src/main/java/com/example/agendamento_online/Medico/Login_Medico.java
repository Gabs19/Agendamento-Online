package com.example.agendamento_online.Medico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agendamento_online.Home;
import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Medico extends AppCompatActivity {

    EditText email, senha;
    TextView reset;
    Button logar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_medico);

        email = (EditText) findViewById(R.id.login_email);
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
                Intent resetar = new Intent(Login_Medico.this, Reset_senha_medico.class);
                startActivity(resetar);
            }
        });
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Login_Medico.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent entrar = new Intent(Login_Medico.this, Home_medico.class);
                            startActivity(entrar);
                        } else {
                            alert("Dados de usuario errados");
                        }
                    }
                });
    }

    private void alert(String msg) {
        Toast.makeText(Login_Medico.this,msg,Toast.LENGTH_SHORT).show();
    }

    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
