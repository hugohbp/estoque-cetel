/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Simulado
 */
@Entity
public class ItensSolicitacao implements Serializable {

    public ItensSolicitacao() {
        this.itensSolicitacaoPK = new ItensSolicitacaoPK();
    }

    @EmbeddedId
    private ItensSolicitacaoPK itensSolicitacaoPK;

    @ManyToOne
    private TipoDoCurso tipoDoCurso;

    private int quantidadeSolicitada;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDevolucao;

    private int quantidadeDevolvida;

    @Lob
    private String observacao;

    private int quantidadeEntregue;

    @ManyToOne
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ItensSolicitacaoPK getItensSolicitacaoPK() {
        return itensSolicitacaoPK;
    }

    public void setItensSolicitacaoPK(ItensSolicitacaoPK itensSolicitacaoPK) {
        this.itensSolicitacaoPK = itensSolicitacaoPK;
    }

    public TipoDoCurso getTipoDoCurso() {
        return tipoDoCurso;
    }

    public void setTipoDoCurso(TipoDoCurso tipoDoCurso) {
        this.tipoDoCurso = tipoDoCurso;
    }



    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public int getQuantidadeDevolvida() {
        return quantidadeDevolvida;
    }

    public void setQuantidadeDevolvida(int quantidadeDevolvida) {
        this.quantidadeDevolvida = quantidadeDevolvida;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public void setQuantidadeSolicitada(int quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public int getQuantidadeEntregue() {
        return quantidadeEntregue;
    }

    public void setQuantidadeEntregue(int quantidadeEntregue) {
        this.quantidadeEntregue = quantidadeEntregue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final ItensSolicitacao other = (ItensSolicitacao) obj;
        if (!Objects.equals(this.itensSolicitacaoPK, other.itensSolicitacaoPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getItensSolicitacaoPK().getProduto().getNome() + " - " + getQuantidadeSolicitada() + " unidades";
    }

}
