/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author Simulado
 */
@Entity
public class ProdutoEFornecedor implements Serializable {

    private ProdutoEFornecedorPK produtoEFornecedorPK = new ProdutoEFornecedorPK();

    private double valor;

    private boolean ativo = true;

    @Column(columnDefinition = "BIT DEFAULT 1")
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @EmbeddedId
    public ProdutoEFornecedorPK getProdutoEFornecedorPK() {
        return produtoEFornecedorPK;
    }

    public void setProdutoEFornecedorPK(ProdutoEFornecedorPK produtoEFornecedorPK) {
        this.produtoEFornecedorPK = produtoEFornecedorPK;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
