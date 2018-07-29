/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.util;

import static br.org.senai.util.Config.ICONE_SISTEMA;
import static br.org.senai.util.Config.NOME_SISTEMA;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Simulado
 */
public class Dialog {

    private static Alert alert;
    private static Notifications notifications;

    private static void mudarIcone(Image image) {

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);

    }

    public static void notificationInformation(String titulo, String conteudo) {

        notifications.create().title(titulo).text(conteudo).showInformation();

    }

    public static void information(String conteudo) {

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        mudarIcone(Config.ICONE_SISTEMA);
        alert.showAndWait();

    }

    public static void information(String conteudo, String titulo) {

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(conteudo);
        mudarIcone(Config.ICONE_SISTEMA);
        alert.showAndWait();

    }

    public static void error(String conteudo) {

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        mudarIcone(Config.ICONE_SISTEMA);
        alert.showAndWait();

    }

    public static void error(String conteudo, String titulo) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        alert.setTitle(titulo);
        mudarIcone(Config.ICONE_SISTEMA);
        alert.showAndWait();
    }

    public static void warning(String conteudo) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        mudarIcone(Config.ICONE_SISTEMA);
        alert.showAndWait();
    }

    public static boolean confirmation(String conteudo) {

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem de confirmação");
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        mudarIcone(Config.ICONE_SISTEMA);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static String inputDialog(String conteudo) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Mensagem de entrada");
        textInputDialog.setHeaderText(null);
        textInputDialog.setContentText(conteudo);
        textInputDialog.setResult(conteudo);

        Stage stage = (Stage) textInputDialog.getDialogPane().getScene().getWindow();

        stage.getIcons().add(Config.ICONE_SISTEMA);

        Optional<String> result = textInputDialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return "";
        }
    }

    public static String inputDialog(String padrao, String conteudo) {
        TextInputDialog textInputDialog = new TextInputDialog(padrao);
        textInputDialog.setTitle("Mensagem de entrada");
        textInputDialog.setHeaderText(null);
        textInputDialog.setContentText(conteudo);
        textInputDialog.setResult(conteudo);

        Stage stage = (Stage) textInputDialog.getDialogPane().getScene().getWindow();

        stage.getIcons().add(Config.ICONE_SISTEMA);

        Optional<String> result = textInputDialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return "";
        }
    }

    public static void abrirJanela(String arquivo, String titulo) {

        Stage stage = new Stage();

        try {
            stage.setScene(new Scene(FXMLLoader.load(Config.class.getResource("/br/org/senai/view/" + arquivo + ".fxml"))));
            stage.getIcons().add(ICONE_SISTEMA);
            stage.setTitle(titulo + " - " + NOME_SISTEMA);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void abrirJanela(String arquivo, String titulo, boolean wait) {

        Stage stage = new Stage();

        try {
            stage.setScene(new Scene(FXMLLoader.load(Config.class.getResource("/br/org/senai/view/" + arquivo + ".fxml"))));
            stage.getIcons().add(ICONE_SISTEMA);
            stage.setTitle(titulo + " - " + NOME_SISTEMA);
            stage.initModality(Modality.APPLICATION_MODAL);

            if (wait) {
                stage.showAndWait();
            } else {
                stage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void abrirJanela(String arquivo, String titulo, boolean wait, boolean maximized, boolean resizable) {

        Stage stage = new Stage();

        try {
            stage.setScene(new Scene(FXMLLoader.load(Config.class.getResource("/br/org/senai/view/" + arquivo + ".fxml"))));
            stage.getIcons().add(ICONE_SISTEMA);
            stage.setTitle(titulo + " - " + NOME_SISTEMA);
            stage.setMaximized(maximized);
            stage.setResizable(resizable);

            if (wait) {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } else {
                stage.initModality(Modality.NONE);
                stage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void abrirJanela(String arquivo, String titulo, boolean wait, boolean maximized, boolean resizable, boolean fecharAplicacao) {

        Stage stage = new Stage();

        try {
            stage.setScene(new Scene(FXMLLoader.load(Config.class.getResource("/br/org/senai/view/" + arquivo + ".fxml"))));
            stage.getIcons().add(ICONE_SISTEMA);
            stage.setTitle(titulo + " - " + NOME_SISTEMA);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMaximized(maximized);
            stage.setResizable(resizable);

            if (fecharAplicacao) {
                stage.setOnCloseRequest((WindowEvent event) -> {
                    Platform.exit();
                    System.exit(0);
                });
            }
            if (wait) {
                stage.showAndWait();
            } else {
                stage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void abrirJanela(String arquivo, String titulo, boolean wait, boolean maximized, boolean resizable, Object userData) {

        Stage stage = new Stage();

        try {
            Parent parent = FXMLLoader.load(Config.class.getResource("/br/org/senai/view/" + arquivo + ".fxml"));
            parent.setUserData(userData);
            stage.setScene(new Scene(parent));
            stage.getIcons().add(ICONE_SISTEMA);
            stage.setTitle(titulo + " - " + NOME_SISTEMA);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMaximized(maximized);
            stage.setResizable(resizable);

            if (wait) {
                stage.showAndWait();
            } else {
                stage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Stage abrirERetornarJanela(String arquivo, String titulo, boolean wait, boolean maximized, boolean resizable) {

        Stage stage = new Stage();

        try {
            stage.setScene(new Scene(FXMLLoader.load(Config.class.getResource("/br/org/senai/view/" + arquivo + ".fxml"))));
            stage.getIcons().add(ICONE_SISTEMA);
            stage.setTitle(titulo + " - " + NOME_SISTEMA);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMaximized(maximized);
            stage.setResizable(resizable);

            if (wait) {
                stage.showAndWait();
            } else {
                stage.show();
            }
        } catch (IOException ex) {
            return null;
        }

        return stage;

    }

    public static Stage retornarJanela(String arquivo, String titulo, boolean maximized, boolean resizable, Object userData) {

        Stage stage = new Stage();

        try {
            Parent parent = FXMLLoader.load(Config.class.getResource("/br/org/senai/view/" + arquivo + ".fxml"));
            parent.setUserData(userData);
            stage.setScene(new Scene(parent));
            stage.getIcons().add(ICONE_SISTEMA);
            stage.setTitle(titulo + " - " + NOME_SISTEMA);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMaximized(maximized);
            stage.setResizable(resizable);

        } catch (IOException ex) {
            return null;
        }

        return stage;
    }

    public static Stage retornarJanela(String arquivo, String titulo, boolean wait, boolean maximized, boolean resizable, Object userData) {

        Stage stage = new Stage();

        try {
            Parent parent = FXMLLoader.load(Config.class.getResource("/br/org/senai/view/" + arquivo + ".fxml"));
            parent.setUserData(userData);
            stage.setScene(new Scene(parent));
            stage.getIcons().add(ICONE_SISTEMA);
            stage.setTitle(titulo + " - " + NOME_SISTEMA);
            stage.setMaximized(maximized);
            stage.setResizable(resizable);

        } catch (IOException ex) {
            return null;
        }

        return stage;
    }

    public static void addNoPainel(Pane pane, String arquivo) {
        pane.getChildren().clear();
        try {
            pane.getChildren().add(FXMLLoader.load(Dialog.class.getResource("/br/org/senai/view/" + arquivo + ".fxml")));
        } catch (IOException ex) {
            Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Node load(String nomeArquivo) {
        try {
            return FXMLLoader.load(Dialog.class.getResource("/br/org/senai/view/" + nomeArquivo + ".fxml"));
        } catch (IOException ex) {
            return null;
        }
    }

}
