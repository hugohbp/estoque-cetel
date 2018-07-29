/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.Compra;

/**
 *
 * @author Simulado
 */
public class CompraDAO extends GenericDAO<Compra>{

    @Override
    public void delete(Compra t) {
        t.setAtivo(false);
        update(t);
    }
    
    
    
}
