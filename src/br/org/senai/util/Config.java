/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.util;

import br.org.senai.entities.Usuario;
import javafx.scene.image.Image;

/**
 *
 * @author Simulado
 */
public class Config {

    public static final Image ICONE_SISTEMA = new Image("file:imagens/iconLogo.png");

    public static final String NOME_SISTEMA = "Estoque CETEL";

    public static final String VERSAO_SISTEMA = "0.0.8";

    public static Usuario usuarioLogado = new Usuario();

    public static final Image ICONE_OK = new Image("file:imagens/icones/ok.png");

    public static final Image ICONE_ERRADO = new Image("file:imagens/icones/errado.png");

    public static final Image ICONE_SEM_IMAGEM = new Image("file:imagens/icones/semFoto.jpg");

    public static final String NOME_JAR = "Estoque CETEL.jar";

    public static String IP_BANCO;
    
    public static String USUARIO_BANCO;
    
    public static String SENHA_BANCO;
    
    public static final String NOME_BANCO = "sisCetel";
    
    public static final String EMAIL_PROVEDOR= "";
    
    public static final String SENHA_PROVEDOR= "";
    
    public static boolean notificacaoAtiva=true;
    
    public static int notificacaoIntervalo=5000;
    
    
}
