package com.example.agendamento_online.Model;

public class Medico extends Usuario {

    private String especialidade;
    private String registro;

    public Medico() {

    }

    public Medico(String id, String nome, String sobrenome, String email, String senha, String celular, String especialidade, String registro) {
        super(id, nome, sobrenome, email, senha, celular);
        this.especialidade = especialidade;
        this.registro = registro;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }


}
