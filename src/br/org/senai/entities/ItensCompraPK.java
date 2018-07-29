/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author Simulado
 */
@Embeddable
public class ItensCompraPK implements Serializable {

    private ProdutoEFornecedor produtoEFornecedor;

    private Compra compra;

    @ManyToOne
    public ProdutoEFornecedor getProdutoEFornecedor() {
        return produtoEFornecedor;
    }

    public void setProdutoEFornecedor(ProdutoEFornecedor produtoEFornecedor) {
        this.produtoEFornecedor = produtoEFornecedor;
    }

    @ManyToOne
    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.produtoEFornecedor);
        hash = 17 * hash + Objects.hashCode(this.compra);
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
        final ItensCompraPK other = (ItensCompraPK) obj;
        if (!Objects.equals(this.produtoEFornecedor, other.produtoEFornecedor)) {
            return false;
        }
        if (!Objects.equals(this.compra, other.compra)) {
            return false;
        }
        return true;
    }
    
    

}
