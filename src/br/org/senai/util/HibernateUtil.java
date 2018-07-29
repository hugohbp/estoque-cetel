/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Simulado
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final Configuration configuration;
    private static ServiceRegistry serviceRegistry;

    static {
        try {

            Ferramenta.lerConexao();

            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://" + Config.IP_BANCO + "/" + Config.NOME_BANCO + "?zeroDateTimeBehavior=convertToNull");
            properties.setProperty("hibernate.connection.username", Config.USUARIO_BANCO);
            properties.setProperty("hibernate.connection.password", Config.SENHA_BANCO);
            

            configuration = new Configuration().configure().addProperties(properties);
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            Dialog.error("Problema de conexão :( \nContate o administrador.");
            throw new ExceptionInInitializerError(ex);

        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
