/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.util;

import br.org.senai.util.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo
 */
public class Conexao {

    public static Connection getConnection(String url, String usuario, String senha) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            return DriverManager.getConnection("jdbc:mysql://" + url + "/"+Config.NOME_BANCO, usuario, senha);
        } catch (SQLException ex) {
            return null;
        }

    }

}
