/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Simulado
 */
@Entity
public class Solicitacao implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;

    private boolean aprovadoSupervisao;

    private boolean entregueManutencao;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNecessidade;

    private boolean cancelada;

    @ManyToOne(cascade = CascadeType.ALL)
    private Status status;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataEntrega;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPrevistaDevolucao;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataDevolucao;

    @Column(columnDefinition = "BIT DEFAULT 1")
    private boolean ativo = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itensSolicitacaoPK.solicitacao")
    private List<ItensSolicitacao> itensSolicitacoes;

    public List<ItensSolicitacao> getItensSolicitacoes() {
        return itensSolicitacoes;
    }

    public void setItensSolicitacoes(List<ItensSolicitacao> itensSolicitacoes) {
        this.itensSolicitacoes = itensSolicitacoes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public boolean isAprovadoSupervisao() {
        return aprovadoSupervisao;
    }

    public void setAprovadoSupervisao(boolean aprovadoSupervisao) {
        this.aprovadoSupervisao = aprovadoSupervisao;
    }

    public boolean isEntregueManutencao() {
        return entregueManutencao;
    }

    public void setEntregueManutencao(boolean entregueManutencao) {
        this.entregueManutencao = entregueManutencao;
    }

    public Date getDataNecessidade() {
        return dataNecessidade;
    }

    public void setDataNecessidade(Date dataNecessidade) {
        this.dataNecessidade = dataNecessidade;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Date getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(Date dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.id;
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
        final Solicitacao other = (Solicitacao) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
