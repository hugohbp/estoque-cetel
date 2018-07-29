/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.LocalDeArmazenamento;

/**
 *
 * @author Simulado
 */
public class LocalDeArmazenamentoDAO extends GenericDAO<LocalDeArmazenamento>{

    @Override
    public void delete(LocalDeArmazenamento t) {
        t.setAtivo(false);
        update(t);
    }
    
    
    
}
