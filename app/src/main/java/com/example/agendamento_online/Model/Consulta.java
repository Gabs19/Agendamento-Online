package com.example.agendamento_online.Model;

public class Consulta {

    private String id;
    private String nomePaciente;
    private String tipoConsulta;
    private String data;
    private String horario;
    private String medico;
    private String precisoes;
    private String Status;
    private String id_paciente;
    private String id_medico;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getMedico() { return medico;}

    public void setMedico(String medico) { this.medico = medico;}

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

    public String getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(String id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getId_medico() {
        return id_medico;
    }

    public void setId_medico(String id_medico) {
        this.id_medico = id_medico;
    }

    //Metodo que faz mostrar o nome do paciente em vez, no list view quando chamo o objeto pessoa.
    @Override
    public String toString() {
        return nomePaciente ;
    }
}
