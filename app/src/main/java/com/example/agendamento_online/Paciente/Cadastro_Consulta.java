package com.example.agendamento_online.Paciente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendamento_online.Home;
import com.example.agendamento_online.Model.Consulta;
import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Cadastro_Consulta extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView nomePaciente, tipoConsulta, data, horario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        nomePaciente = findViewById(R.id.nome_paciente);
        tipoConsulta = findViewById(R.id.tipo_consulta);
        data = findViewById(R.id.data);
        horario = findViewById(R.id.horario);

        Button agendar = (Button) findViewById(R.id.cadastrar_agendamento);

        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarAgendamento();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void cadastrarAgendamento() {
        Consulta consulta = new Consulta();

        consulta.setId(UUID.randomUUID().toString());
        consulta.setNomePaciente(nomePaciente.getText().toString().trim());
        consulta.setTipoConsulta(tipoConsulta.getText().toString().trim());
        consulta.setData(data.getText().toString().trim());
        consulta.setHorario(horario.getText().toString().trim());
        consulta.setId_paciente(user.getUid());
        consulta.setPrecisoes("Aguarde confirmação do Medico.");
        consulta.setStatus("Ativo");

        String horario [] = consulta.getHorario().split(":");
        int hora = Integer.parseInt(horario[0]);
        int minuto = Integer.parseInt(horario[1]);

        String data [] = consulta.getData().split("/");
        int dia = Integer.parseInt(data[0]);
        int mes = Integer.parseInt(data[1]);
        int ano = Integer.parseInt(data[2]);

        if((dia >= 1 && dia <= 31) && (mes >= 1 && mes <= 12) && (ano >= 2020)){
            if ((hora >= 7 && hora <= 17) && (minuto >= 0 && minuto <= 59)) {
                databaseReference.child("Paciente").child(user.getUid()).child("Consulta").child(consulta.getId()).setValue(consulta);
                Intent home = new Intent(Cadastro_Consulta.this, Home.class);
                startActivity(home);
            } else { alert("Formato de Horario errado. Por favor, adicione entre 07:00 ~ 17:00"); }
        } else { alert("Formato da data errado. Por favor, adicione formato dd/mm/aaaa."); }
    }

    private void alert(String msg) {
        Toast.makeText(Cadastro_Consulta.this,msg,Toast.LENGTH_SHORT).show();
    }
}