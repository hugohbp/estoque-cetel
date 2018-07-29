/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.util;

import br.org.senai.dao.GrupoDAO;
import br.org.senai.dao.StatusDAO;
import br.org.senai.dao.UsuarioDAO;
import br.org.senai.entities.Grupo;
import br.org.senai.entities.Usuario;
import static br.org.senai.util.Config.ICONE_SISTEMA;
import static br.org.senai.util.Config.NOME_SISTEMA;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Simulado
 */
public class Main extends Application {

    @Override
    public void init() throws Exception {
        new UsuarioDAO().listarTodos();
    }   

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("/br/org/senai/view/" + "Login" + ".fxml"))));
        primaryStage.getIcons().add(ICONE_SISTEMA);
        primaryStage.setTitle("Login - " + NOME_SISTEMA);
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.show();

        Platform.runLater(() -> {
            Ferramenta.lerOpcoes();
            if (new UsuarioDAO().listarTodos().isEmpty()) {
                Usuario usuario = new Usuario();

                usuario.setEmail("hugohbp1@gmail.com");
                usuario.setLogin("1");
                usuario.setNome("Hugo");
                usuario.setSenha(Encripta.criptografarSenha("1"));

                if (new GrupoDAO().listarTodos().isEmpty()) {
                    new GrupoDAO().save(new Grupo("Supervisor"));
                }

                usuario.setGrupo(new GrupoDAO().get(1));
                new UsuarioDAO().save(usuario);
            }
            
            System.out.println(StatusDAO.emAberto);
            System.out.println(StatusDAO.aprovadoParcialmente);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
