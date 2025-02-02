 package com.example.agendamento_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agendamento_online.Medico.Inicio;
import com.example.agendamento_online.Model.Medico;
import com.example.agendamento_online.Paciente.Login_Paciente;
import com.example.agendamento_online.Paciente.Registro_Paciente;

 public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cadastro = (Button) findViewById(R.id.cadastrar);
        Button login = (Button) findViewById(R.id.login);
        TextView medico = (TextView) findViewById(R.id.medico);

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro = new Intent(MainActivity.this, Registro_Paciente.class);
                startActivity(cadastro);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entrar = new Intent(MainActivity.this, Login_Paciente.class);
                startActivity(entrar);
            }
        });

        medico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMedico = new Intent(MainActivity.this, Inicio.class);
                startActivity(mainMedico);
            }
        });

    }
}