package com.example.agendamento_online.Paciente.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.agendamento_online.MainActivity;
import com.example.agendamento_online.Model.Consulta;
import com.example.agendamento_online.Paciente.Cadastro_Consulta;
import com.example.agendamento_online.R;
import com.example.agendamento_online.authentication.Conexao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomePacienteFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ListView list_consulta;

    private List<Consulta> consultas = new ArrayList<Consulta>();
    private ArrayAdapter<Consulta> consultaArrayAdapter;

    Consulta consultaSelecionada;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_paciente, container,false);

        list_consulta = (ListView) view.findViewById(R.id.list_consulta);

        Button agendarConsulta = (Button) view.findViewById(R.id.cadastrar_consulta);

        agendarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agendamento = new Intent(getActivity(), Cadastro_Consulta.class);
                startActivity(agendamento);
            }
        });

        list_consulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                consultaSelecionada = (Consulta) parent.getItemAtPosition(position);
                String nome = consultaSelecionada.getNomePaciente();
                String data = consultaSelecionada.getData();
                String horario = consultaSelecionada.getHorario();
                String consulta = consultaSelecionada.getTipoConsulta();

                read(nome, data, horario,consulta);

            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        verificarUser();
    }

    private void verificarUser() {

        if (user == null) {
            Intent principal = new Intent(getActivity(), MainActivity.class);
            startActivity(principal);
        } else {
            //mostra as consultas cadastrada desse usuario
            DatabaseReference consultaReferente = database.getReference().child("Paciente").child(user.getUid()).child("Consulta");

            consultaReferente.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    consultas.clear();
                    for (DataSnapshot obj : snapshot.getChildren()) {
                        String status = obj.child("status").getValue(String.class);
                        if (status.equals("Ativo")) {
                            Consulta consulta = obj.getValue(Consulta.class);
                            consultas.add(consulta);
                        }
                    }
                    consultaArrayAdapter = new ArrayAdapter<Consulta>(getActivity(), android.R.layout.simple_list_item_1, consultas);
                    list_consulta.setAdapter(consultaArrayAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void read(String nome, String data, String horario,String consulta) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Consulta");
        builder.setMessage("Nome do paciente : " + nome + "\nConsulta : " + consulta + "\nData da consulta : " + data + "\nHorario da consulta : " + horario);
        builder.create();
        builder.show();
    }
}
