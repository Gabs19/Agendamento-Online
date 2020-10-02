package com.example.agendamento_online.Paciente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Consulta extends AppCompatActivity {

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

        inicializarFirebase();

        Button agendar = (Button) findViewById(R.id.cadastrar_agendamento);

        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarAgendamento();
                finish();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Consulta.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    private void cadastrarAgendamento() {
        com.example.agendamento_online.Model.Consulta consulta = new com.example.agendamento_online.Model.Consulta();

        consulta.setId(UUID.randomUUID().toString());
        consulta.setNomePaciente(nomePaciente.getText().toString().trim());
        consulta.setTipoConsulta(tipoConsulta.getText().toString().trim());
        consulta.setData(data.getText().toString().trim());
        consulta.setHorario(horario.getText().toString().trim());
        consulta.setId_paciente(user.getUid());
        consulta.setPrecisoes("Aguarde confirmação do Medico.");
        consulta.setStatus("Ativo");

        databaseReference.child("Consulta").child(consulta.getId()).setValue(consulta);

    }

}