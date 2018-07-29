/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.entities;

import java.io.Serializable;
import java.util.List;
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
public class TipoDoCurso implements Serializable {

    @OneToMany(mappedBy = "tipoDoCurso")
    private List<ItensCompra> itensCompras;
    @OneToMany(mappedBy = "tipoDoCurso")
    private List<ItensSolicitacao> itensSolicitacaos;

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String descricao;

    @Column(unique = true, columnDefinition = "INT (11) DEFAULT 0 ")
    private int centroDeCusto=0;

    @Column(columnDefinition = "BIT DEFAULT 1")
    private boolean ativo = true;

    public TipoDoCurso(String descricao, int centroDeCusto) {
        this.descricao = descricao;
        this.centroDeCusto = centroDeCusto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public TipoDoCurso() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItensCompra> getItensCompras() {
        return itensCompras;
    }

    public void setItensCompras(List<ItensCompra> itensCompras) {
        this.itensCompras = itensCompras;
    }

    public List<ItensSolicitacao> getItensSolicitacaos() {
        return itensSolicitacaos;
    }

    public void setItensSolicitacaos(List<ItensSolicitacao> itensSolicitacaos) {
        this.itensSolicitacaos = itensSolicitacaos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCentroDeCusto() {
        return centroDeCusto;
    }

    public void setCentroDeCusto(int centroDeCusto) {
        this.centroDeCusto = centroDeCusto;
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
        final TipoDoCurso other = (TipoDoCurso) obj;
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
