package com.example.agendamento_online;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendamento_online.Model.Consulta;

import java.util.ArrayList;
import java.util.List;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.ConsultaViewHolder> {
    private final List<Consulta> consultas;

    public ConsultaAdapter(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @NonNull
    @Override
    public ConsultaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_item, parent, false);
        return new ConsultaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultaViewHolder holder, int position) {
        Consulta consulta = consultas.get(position);
        holder.bind(consulta);

    }

    @Override
    public int getItemCount() {
        return consultas.size();
    }

    class ConsultaViewHolder extends RecyclerView.ViewHolder {

        TextView nomePaciente;
        TextView emailPaciente;
        TextView celularPaciente;
        TextView txtIcon;
        TextView txtDate;

        public ConsultaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomePaciente = itemView.findViewById(R.id.nome_paciente);
            emailPaciente = itemView.findViewById(R.id.email_paciente);
            celularPaciente = itemView.findViewById(R.id.celular_paciente);
            txtIcon = itemView.findViewById(R.id.txt_icon);
            txtDate = itemView.findViewById(R.id.txt_date);
        }

        public void bind(Consulta consulta) {
            nomePaciente.setText(consulta.getNomePaciente());
            emailPaciente.setText(consulta.getHorario());
            txtIcon.setText(String.valueOf(consulta.getNomePaciente().charAt(0)));
            txtDate.setText(consulta.getData());
        }
    }

}
