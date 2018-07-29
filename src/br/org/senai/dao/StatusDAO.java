/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.Status;

/**
 *
 * @author Simulado
 */
public class StatusDAO extends GenericDAO<Status> {

    @Override
    public void delete(Status t) {
        t.setAtivo(false);
        update(t);
    }

    public Status getStatus(String descricao) {
        objeto = (Status) session.createQuery("select s from Status s where s.descricao=:descricao").setString("descricao", descricao).uniqueResult();
        fecharSessao();
        return objeto;
    }

    public static final Status emAberto = new StatusDAO().get(1);

    public static final Status aprovadoSupervisao = new StatusDAO().get(2);

    public static final Status emAprovação = new StatusDAO().get(3);

    public static final Status aprovadoParcialmente = new StatusDAO().get(4);

    public static final Status emAndamento = new StatusDAO().get(5);

    public static final Status fechado = new StatusDAO().get(6);

    public static final Status disponivelParaRetirada = new StatusDAO().get(7);

}
