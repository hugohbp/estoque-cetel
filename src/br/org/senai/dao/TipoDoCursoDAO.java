/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.TipoDoCurso;

/**
 *
 * @author Simulado
 */
public class TipoDoCursoDAO extends GenericDAO<TipoDoCurso> {

    @Override
    public void delete(TipoDoCurso t) {
        t.setAtivo(false);
        update(t);
    }
    
    
    
    
}
