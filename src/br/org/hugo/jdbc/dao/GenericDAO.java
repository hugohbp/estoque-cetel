/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.hugo.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Hugo
 */
abstract class GenericDAO<T> {

    public abstract int count() throws SQLException;

    protected Connection con;
    protected PreparedStatement preparedStatement;
    protected Statement statement;
    protected ResultSet resultSet;

    protected GenericDAO() {
        this.con = ConnectionFactory.getConnection();
    }

//    private String gerarClasse() {
//        String[] list = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName().split("\\.");
//        return list[list.length - 1];
//    }

    public void fecharSessao() {
        DbUtil.close(resultSet);
        DbUtil.close(preparedStatement);
        DbUtil.close(con);
    }
    
    public abstract void salvar(T t);
    public abstract List<T> listarTodos();

}
