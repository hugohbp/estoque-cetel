/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.ProdutoEFornecedor;

/**
 *
 * @author Simulado
 */
public class ProdutoEFornecedorDAO extends GenericDAO<ProdutoEFornecedor> {

    @Override
    public void delete(ProdutoEFornecedor t) {
       t.setAtivo(false);
        update(t);
    }
    
    
    
}
