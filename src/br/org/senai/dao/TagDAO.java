/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.Tag;

/**
 *
 * @author Simulado
 */
public class TagDAO extends GenericDAO<Tag> {

    @Override
    public void delete(Tag t) {
        t.setAtivo(false);
        update(t);
    }
    
    
    
}
