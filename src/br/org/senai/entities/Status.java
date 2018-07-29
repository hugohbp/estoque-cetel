/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Simulado
 */
@Entity
public class Status implements Serializable {

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<Solicitacao> solicitacaos;

    @Id
    @GeneratedValue
    private int id;

    private String descricao;

    @Column(columnDefinition = "BIT DEFAULT 1")
    private boolean ativo = true;

    public List<Solicitacao> getSolicitacaos() {
        return solicitacaos;
    }

    public void setSolicitacaos(List<Solicitacao> solicitacaos) {
        this.solicitacaos = solicitacaos;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Status(String descricao) {
        this.descricao = descricao;
    }

    public Status() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Status other = (Status) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

}
