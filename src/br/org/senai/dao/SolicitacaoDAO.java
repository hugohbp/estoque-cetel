/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.Solicitacao;
import br.org.senai.entities.Usuario;
import br.org.senai.util.Conexao;
import br.org.senai.util.Config;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Simulado
 */
public class SolicitacaoDAO extends GenericDAO<Solicitacao> {

    @Override
    public void delete(Solicitacao t) {
        t.setAtivo(false);
        update(t);
    }

    public List<Solicitacao> pegarSolicitacoesInstrutor() {
        lista = session.createQuery("select s from Solicitacao s where s.aprovadoSupervisao = false and s.status in (:status)").setParameterList("status", new Object[]{StatusDAO.emAberto}).list();
        fecharSessao();
        return lista;
    }

    public List<Solicitacao> pegarSolicitacoesInstrutorNotificacao(Usuario usuario) {
        lista = session.createQuery("select s from Solicitacao s where s.status in (:status) and s.usuario=:usuario").setParameterList("status", new Object[]{StatusDAO.aprovadoParcialmente, StatusDAO.disponivelParaRetirada}).setEntity("usuario", usuario).list();
        fecharSessao();
        return lista;
    }

    public List<Solicitacao> pegarSolicitacoesManutencao() {
        lista = session.createQuery("select s from Solicitacao s where s.aprovadoSupervisao = true and s.ativo= true and s.status in (:status)").setParameterList("status", new Object[]{StatusDAO.emAndamento, StatusDAO.aprovadoParcialmente, StatusDAO.aprovadoSupervisao}).list();
        fecharSessao();
        return lista;
    }

    public List<Solicitacao> pegarSolicitacaoDoUsuario(Usuario usuario) {
        lista = session.createQuery("select s from Solicitacao s where s.usuario=:usuario").setEntity("usuario", usuario).list();
        fecharSessao();
        return lista;
    }

}
