package com.example.agendamento_online.Medico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agendamento_online.MainActivity;
import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_senha_medico extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha_medico);

        final EditText verifica_email = (EditText) findViewById(R.id.verifica_senha);

        Button reseta = (Button) findViewById(R.id.resetar);

        reseta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = verifica_email.getText().toString().trim();
                resetaSenha(email);
            }
        });

    }

    void resetaSenha(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(Reset_senha_medico.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            alert("Email foi enviado para alterar sua senha");

                            Intent voltar = new Intent(Reset_senha_medico.this, MainActivity.class);
                            startActivity(voltar);

                        } else {
                            alert("Email não cadastrado.");
                        }
                    }
                });
    }

    void alert(String msg) {
        Toast.makeText(Reset_senha_medico.this,msg,Toast.LENGTH_SHORT).show();
    }

    protected  void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
