package com.example.agendamento_online.Model;

public class Consulta {

    private String nomePaciente;
    private String tipoConsulta;
    private String data;
    private String horario;
//    private String medico;
    private String precisoes;
    private String Status;

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getPrecisoes() {
        return precisoes;
    }

    public void setPrecisoes(String precisoes) {
        this.precisoes = precisoes;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
