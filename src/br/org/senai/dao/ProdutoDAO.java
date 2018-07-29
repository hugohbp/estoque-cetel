/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.Produto;
import java.util.List;

/**
 *
 * @author Simulado
 */
public class ProdutoDAO extends GenericDAO<Produto> {

    @Override
    public void delete(Produto t) {
        t.setAtivo(false);
        update(t);
    }

    public List<Produto> getProdutoPorNome(String nome) {
        lista = session.createQuery("select p from Produto p where LOWER(p.nome) like LOWER('%" + nome + "%') and p.ativo=true").list();
        fecharSessao();
        return lista;
    }

}
