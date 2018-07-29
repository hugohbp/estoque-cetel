/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.Fornecedor;

/**
 *
 * @author Simulado
 */
public class FornecedorDAO extends GenericDAO<Fornecedor> {

    @Override
    public void delete(Fornecedor t) {
        t.setAtivo(false);
        update(t);
    }

    public Fornecedor validarFornecedor(Fornecedor fornecedor) {
        objeto = (Fornecedor) session.createQuery("select f from Fornecedor f where f.nome=:nome").setString("nome", fornecedor.getNome()).uniqueResult();
        fecharSessao();
        return objeto;
    }

}
