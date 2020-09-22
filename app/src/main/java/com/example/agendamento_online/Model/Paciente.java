package com.example.agendamento_online.Model;

public class Paciente extends Usuario {

    private String cpf;

    public Paciente () {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
