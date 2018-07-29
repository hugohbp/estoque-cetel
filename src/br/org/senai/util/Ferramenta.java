/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.util;

import br.org.senai.dao.UsuarioDAO;
import br.org.senai.entities.Usuario;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Simulado
 */
public class Ferramenta {

    public static <T> void fillComboBox(ComboBox comboBox, List list) {

        ObservableList<T> list1 = FXCollections.observableArrayList();

        list1.setAll(list);

        comboBox.setItems(list1);

        comboBox.setPromptText(">Selecione<");

    }

    public static byte[] converterArquivoParaByte(File file) {
        try {
            return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        } catch (IOException ex) {
            return null;
        }
    }

    public static String formatarData(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public static Image converterByteParaImage(byte[] array) {
        return new Image(new ByteArrayInputStream(array));
    }

    public static void lerConexao() {

        try {

            File file = new File("etc");

            if (!file.exists()) {
                if (file.mkdirs()) {
                    salvarConexao("localhost", "root", "1234", false);
                }
            }

            List<String> linhas = new ArrayList<>();

            try {
                linhas = Files.readAllLines(Paths.get("etc/banco.txt"), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            Encripta encriptacaoAES = new Encripta();

            Config.IP_BANCO = encriptacaoAES.desencriptar(linhas.get(0));
            Config.USUARIO_BANCO = encriptacaoAES.desencriptar(linhas.get(1));
            Config.SENHA_BANCO = encriptacaoAES.desencriptar(linhas.get(2));

        } catch (Exception ex) {
            Dialog.error(ex.getMessage());
        }

    }

    public static void salvarConexao(String ip, String usuario, String senha, boolean reiniciar) {

        Encripta encripta = new Encripta();
        List<String> linhas = new ArrayList<>();

        linhas.add(encripta.encriptar(ip));
        linhas.add(encripta.encriptar(usuario));
        linhas.add(encripta.encriptar(senha));

        if (verificarConexao(ip, usuario, senha)) {

            try {
                Files.write(Paths.get("etc/banco.txt"), linhas, StandardCharsets.UTF_8);

                if (reiniciar) {
                    Dialog.information("Configurações alteradas com sucesso.\n-> A aplicação irá reiniciar.");

                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("java -jar " + Config.NOME_JAR);

                    System.exit(0);
                } else {
                }
            } catch (IOException ex) {
                Logger.getLogger(Ferramenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void salvarOpcoes(String ativo, String intervalo) {
        Encripta encripta = new Encripta();
        List<String> linhas = new ArrayList<>();

        linhas.add(ativo);
        linhas.add(intervalo);

        try {
            Files.write(Paths.get("etc/config_estoque.txt"), linhas, StandardCharsets.UTF_8);
            Dialog.information("Salvo.");

        } catch (IOException ex) {
            Logger.getLogger(Ferramenta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void lerOpcoes() {
        List<String> linhas = new ArrayList<>();

        try {
            linhas = Files.readAllLines(Paths.get("etc/config_estoque.txt"), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

//        Config.notificacaoAtiva = linhas.get(0).equals("true");
//        Config.notificacaoIntervalo = Integer.parseInt(linhas.get(1));
    }

    public static boolean verificarConexao(String url, String usuario, String senha) {

        boolean retorno = false;
        Connection connection = Conexao.getConnection(url, usuario, senha);

        try {
            if (connection != null) {
                if (!connection.isClosed()) {
                    connection.close();
                    retorno = true;
                }
            }
        } catch (SQLException ex) {
        }
        return retorno;

    }

    public static String numeroAleatorio() {
        String AB = "0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static void configurarImagemNoBotao(Button button, String nomeArquivo, String tooltip) {
        ImageView imageView1 = new ImageView("file:imagens/icones/" + nomeArquivo);
        imageView1.setPreserveRatio(true);
        imageView1.setFitHeight(50);
        button.setGraphic(imageView1);
        button.setTooltip(new Tooltip(tooltip));
        button.setContentDisplay(ContentDisplay.TOP);
    }

    public static boolean validarEmail(String texto) {
        try {
            if (texto.matches("[a-z0-9]{4,}@[a-z]+(.com|.com.[a-z]+)")) {
                Config.usuarioLogado.setEmail(texto);
                return new UsuarioDAO().validarCadastroDeEmail(Config.usuarioLogado);
            } else {
                return false;
            }
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public static boolean validarLogin(String login) {
        Usuario usuario = new Usuario();
        usuario.setLogin(login);

        if (new UsuarioDAO().validarLogin(usuario) == null && login != null) {
            return true;
        } else {
            return false;
        }
    }

}
