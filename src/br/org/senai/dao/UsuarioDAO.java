/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.Usuario;

/**
 *
 * @author Simulado
 */
public class UsuarioDAO extends GenericDAO<Usuario> {

    @Override
    public void delete(Usuario t) {
        t.setAtivo(false);
        update(t);
    }

    public Usuario validarLogin(Usuario usuario) {
        objeto = (Usuario) session.createQuery("select u from Usuario u where u.login=:login and u.id!=:id").setString("login", usuario.getLogin()).setInteger("id", usuario.getId()).uniqueResult();
        fecharSessao();
        return objeto;
    }

    public boolean validarCadastroDeEmail(Usuario usuario) {

        objeto = (Usuario) session.createQuery("select u from Usuario u where u.email=:email and u.id!=:id").setString("email", usuario.getEmail()).setInteger("id", usuario.getId()).uniqueResult();
        fecharSessao();

        if (objeto != null) {
            System.out.println(" encontrado");
            return false;
        } else {
            System.out.println("nao encontrado");
            return true;
        }

    }

    public Usuario pegarEmail(Usuario usuario) {
        objeto = (Usuario) session.createQuery("select u from Usuario u where u.email=:email").setString("email", usuario.getEmail()).uniqueResult();
        fecharSessao();
        return objeto;
    }

    public Usuario logarValidarLogin(Usuario usuario) {
        objeto = (Usuario) session.createQuery("select u from Usuario u where u.login=:login").setString("login", usuario.getLogin()).uniqueResult();
        fecharSessao();
        return objeto;
    }

    public Usuario logarValidarLoginESenha(Usuario usuario) {
        objeto = (Usuario) session.createQuery("select u from Usuario u where u.login=:login and u.senha=:senha").setString("login", usuario.getLogin()).setString("senha", usuario.getSenha()).uniqueResult();
        fecharSessao();
        return objeto;
    }

}
