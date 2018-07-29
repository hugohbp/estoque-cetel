/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Simulado
 * @param <T>
 */
public interface InterfaceDAO<T> {

    public void save(T t);

    public void update(T t);

    public void delete(T t);

    public T get(Serializable id);

    public List<T> listarTodos();

}
