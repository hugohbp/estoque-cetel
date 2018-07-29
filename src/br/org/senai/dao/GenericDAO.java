/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.dao;

import br.org.senai.util.HibernateUtil;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Simulado
 * @param <T>
 */
public abstract class GenericDAO<T> implements InterfaceDAO<T> {
    
    protected Session session;
    protected Class classe;
    protected T objeto;
    protected List<T> lista;
    
    public GenericDAO() {        
        classe = retornaClasse();
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();        
    }
    
    private Class<T> retornaClasse() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    protected void fecharSessao() {
        session.getTransaction().commit();
        session.close();
    }
    
    @Override
    public void delete(T t) {
        session.delete(t);
        fecharSessao();
    }
    
    @Override
    public T get(Serializable id) {
        objeto = (T) session.get(classe, id);
        fecharSessao();
        return objeto;
    }
    
    @Override
    public List<T> listarTodos() {
        lista = session.createCriteria(classe).add(Restrictions.or(Restrictions.eq("ativo", true))).list();
        fecharSessao();
        return lista;
    }
    
    @Override
    public void save(T t) {
        session.save(t);
        fecharSessao();
    }
    
    @Override
    public void update(T t) {
        session.update(t);
        fecharSessao();
    }
    
}
