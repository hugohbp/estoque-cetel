/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.ItensSolicitacao;
import br.org.senai.entities.Solicitacao;
import br.org.senai.util.Conexao;
import br.org.senai.util.Config;
import java.util.List;

/**
 *
 * @author Simulado
 */
public class ItensSolicitacaoDAO extends GenericDAO<ItensSolicitacao> {

    public List<ItensSolicitacao> getDaSolicitacao(Solicitacao solicitacao) {

        lista = session.createQuery("select i FROM ItensSolicitacao i where i.itensSolicitacaoPK.solicitacao=:solicitacao").setEntity("solicitacao", solicitacao).list();
        fecharSessao();
        return lista;
    }

}
