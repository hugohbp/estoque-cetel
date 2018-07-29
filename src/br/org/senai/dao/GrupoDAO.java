/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.entities.Grupo;

/**
 *
 * @author Simulado
 */
public class GrupoDAO extends GenericDAO<Grupo> {

    @Override
    public void delete(Grupo t) {
        t.setAtivo(false);
        update(t);
    }

}
