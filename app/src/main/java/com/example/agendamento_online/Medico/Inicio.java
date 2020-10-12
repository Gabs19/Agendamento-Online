package com.example.agendamento_online.Medico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agendamento_online.R;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_medico);

        Button cadastro = (Button) findViewById(R.id.cadastrar);
        Button login = (Button) findViewById(R.id.login);

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro = new Intent(Inicio.this, Registro_Medico.class);
                startActivity(cadastro);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entrar = new Intent(Inicio.this, Login_Medico.class);
                startActivity(entrar);
            }
        });

    }
}

