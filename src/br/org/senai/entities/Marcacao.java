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
public class Marcacao implements Serializable {

    private MarcacaoPK marcacaoPK = new MarcacaoPK();

    private boolean ativo = true;

    @Column(columnDefinition = "BIT DEFAULT 1")
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Marcacao() {
    }

    public Marcacao(Tag tag, Produto produto) {
        getMarcacaoPK().setTag(tag);
        getMarcacaoPK().setProduto(produto);
    }

    @EmbeddedId
    public MarcacaoPK getMarcacaoPK() {
        return marcacaoPK;
    }

    public void setMarcacaoPK(MarcacaoPK marcacaoPK) {
        this.marcacaoPK = marcacaoPK;
    }

}
