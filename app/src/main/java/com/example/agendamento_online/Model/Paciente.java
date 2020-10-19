package com.example.agendamento_online.Model;

public class Paciente extends Usuario {

    private String cpf;

    public Paciente(){

    }

    public Paciente(String id, String nome, String sobrenome, String email, String senha, String celular, String cpf) {
        super(id, nome, sobrenome, email, senha, celular);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
