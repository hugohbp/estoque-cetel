/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.Marcacao;
import br.org.senai.entities.Produto;
import br.org.senai.entities.Tag;
import java.util.List;

/**
 *
 * @author Simulado
 */
public class MarcacaoDAO extends GenericDAO<Marcacao> {

//    @Override
//    public void delete(Marcacao t) {
//        t.setAtivo(true);
//        update(t);
//    }
    public List<Tag> getTagsDoProduto(Produto produto) {
        List<Tag> tags = session.createQuery("SELECT t.marcacaoPK.tag from Marcacao t where t.marcacaoPK.produto=:produto").setEntity("produto", produto).list();
        fecharSessao();
        return tags;
    }

    public void limparTags(Produto produto) {
        System.out.println(produto.getId());
        System.out.println(session.createQuery("DELETE from Marcacao where marcacaoPK.produto.id=:produto").setInteger("produto", produto.getId()).executeUpdate());
        fecharSessao();
    }

}
