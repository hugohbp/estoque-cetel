/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Simulado
 */
@Entity
public class Compra implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private double valorTotal;

    @Temporal(TemporalType.DATE)
    private Date dataCompra;

    @Temporal(TemporalType.DATE)
    private Date dataCompraRecebida;

    private String nNotaFiscal;

    @ManyToOne
    private Usuario usuario;

    @Column(columnDefinition = "BIT", length = 1)
    private boolean ativo = true;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getnNotaFiscal() {
        return nNotaFiscal;
    }

    public void setnNotaFiscal(String nNotaFiscal) {
        this.nNotaFiscal = nNotaFiscal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Date getDataCompraRecebida() {
        return dataCompraRecebida;
    }

    public void setDataCompraRecebida(Date dataCompraRecebida) {
        this.dataCompraRecebida = dataCompraRecebida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
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
        final Compra other = (Compra) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
