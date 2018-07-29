/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Simulado
 */
@Entity
public class ItensCompra implements Serializable {

    private ItensCompraPK itensCompraPK = new ItensCompraPK();

    private TipoDoCurso tipoDoCurso;

    private int quantidadeSolicitada;

    private int quantidadeRecebida;

    private Date dataRecebimento;

    private Status status;

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public void setQuantidadeSolicitada(int quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public int getQuantidadeRecebida() {
        return quantidadeRecebida;
    }

    public void setQuantidadeRecebida(int quantidadeRecebida) {
        this.quantidadeRecebida = quantidadeRecebida;
    }

    @ManyToOne
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @EmbeddedId
    public ItensCompraPK getItensCompraPK() {
        return itensCompraPK;
    }

    public void setItensCompraPK(ItensCompraPK itensCompraPK) {
        this.itensCompraPK = itensCompraPK;
    }

    @ManyToOne
    public TipoDoCurso getTipoDoCurso() {
        return tipoDoCurso;
    }

    public void setTipoDoCurso(TipoDoCurso tipoDoCurso) {
        this.tipoDoCurso = tipoDoCurso;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

}
