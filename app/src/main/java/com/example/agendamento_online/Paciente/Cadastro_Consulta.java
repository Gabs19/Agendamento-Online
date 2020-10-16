package com.example.agendamento_online.Paciente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendamento_online.Home;
import com.example.agendamento_online.Model.Consulta;
import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class Cadastro_Consulta extends AppCompatActivity {

    private String[] tiposConsulta = new String[]{"Escolha sua consulta: ",
            "Clínica Geral", "Cardiologia", "Gastroentereologia", "Obstetrícia", "Pediatria", "Ortopedia", "Fonoaudiologia",
            "Endocrinologia", "Pneumologia", "Urologia", "Dermatologia", "Oftalmologia", "Otorrinolaringologia", "Neurologia"};

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView nomePaciente, data, horario;
    private Spinner tipoConsulta;

    private String c, m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        nomePaciente = findViewById(R.id.nome_paciente);
        data = findViewById(R.id.data);
        horario = findViewById(R.id.horario);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tiposConsulta);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipoConsulta = (Spinner) findViewById(R.id.tipo_consulta);
        tipoConsulta.setAdapter(adapter);

        tipoConsulta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c = tiposConsulta[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        consulta.setTipoConsulta(c.trim());
        consulta.setData(data.getText().toString().trim());
        consulta.setHorario(horario.getText().toString().trim());
        consulta.setId_paciente(user.getUid());
        consulta.setPrecisoes("Aguarde confirmação do Medico.");
        consulta.setStatus("Ativo");

        String horario[] = consulta.getHorario().split(":");
        int hora = Integer.parseInt(horario[0]);
        int minuto = Integer.parseInt(horario[1]);

        String data[] = consulta.getData().split("/");
        int dia = Integer.parseInt(data[0]);
        int mes = Integer.parseInt(data[1]);
        int ano = Integer.parseInt(data[2]);

        final Calendar c = Calendar.getInstance();

        if (ano >= c.get(Calendar.YEAR)) {
            if (mes >= c.get(Calendar.MONTH) || (mes < c.get(Calendar.MONTH) && ano > c.get(Calendar.YEAR))) {
                if (dia > c.get(Calendar.DAY_OF_MONTH)) {
                    if ((hora >= 7 && hora < 12 || hora >= 13 && hora <= 17) && (minuto >= 0 && minuto <= 59)) {
                        databaseReference.child("Consulta").child(consulta.getId()).setValue(consulta);
                        Intent home = new Intent(Cadastro_Consulta.this, Home.class);
                        startActivity(home);
                    } else {
                        alert("Formato de Horario errado. Por favor, adicione entre 07:00 ~ 17:00");
                    }
                } else {
                    alert("Formato da data errado. Por favor, adicione formato dd/mm/aaaa.");
                }
            } else {
                alert("Formato da data errado. Por favor, adicione formato dd/mm/aaaa.");
            }
        } else {
            alert("Formato da data errado. Por favor, adicione formato dd/mm/aaaa.");
        }

    }


    private void alert(String msg) {
        Toast.makeText(Cadastro_Consulta.this, msg, Toast.LENGTH_SHORT).show();
    }
}