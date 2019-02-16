package br.com.finchsolucoes.testeAutomacao.entity;

import javax.persistence.*;

@Entity
@Table(name = "jogos")
public class Jogo  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String campeonato;
    private String data;
    private String horario;
    private String clube_mandante;
    private String clube_visitante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(String campeonato) {
        this.campeonato = campeonato;
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

    public String getClube_mandante() {
        return clube_mandante;
    }

    public void setClube_mandante(String clube_mandante) {
        this.clube_mandante = clube_mandante;
    }

    public String getClube_visitante() {
        return clube_visitante;
    }

    public void setClube_visitante(String clube_visitante) {
        this.clube_visitante = clube_visitante;
    }

    public boolean isValid() {
        return getCampeonato() != null && getData() != null && getHorario() != null && getClube_mandante() != null
                && getClube_visitante() != null;
    }
}
