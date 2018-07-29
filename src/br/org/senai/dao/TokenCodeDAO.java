/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.TokenCode;

/**
 *
 * @author Simulado
 */
public class TokenCodeDAO extends GenericDAO<TokenCode> {

    public TokenCode pegarToken(TokenCode tokenCode) {
        objeto = (TokenCode) session.createQuery("select t from TokenCode t where t.ativo=true and t.token=:token and t.usuario=:usuario").setString("token", tokenCode.getToken()).setEntity("usuario", tokenCode.getUsuario()).uniqueResult();
        fecharSessao();
        return objeto;
    }

}
