/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.util.Config;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CabecalhoMenuController implements Initializable {

    @FXML
    private Label lbBemVindo;

    @FXML
    private ImageView fotoUsuario;

    @FXML
    private Label lbNomeDoSistema;

    

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pane.setStyle(
                "-fx-background-insets: 1; "
                + "-fx-background-radius: 3; "
        );

        lbNomeDoSistema.setText(Config.NOME_SISTEMA);

        fotoUsuario.setImage(Config.ICONE_SISTEMA);

       

   
      
        
        
    }

    @FXML
    public void abrirSolicitarItens() {
        


    }

    @FXML
    public void logoff() {
        Platform.runLater(() -> {

            Stage menuPrincipal = new Stage();

            try {

                menuPrincipal.setScene(new Scene(FXMLLoader.load(getClass().getResource("/br/org/senai/view/Login.fxml"))));

                menuPrincipal.getIcons().add(Config.ICONE_SISTEMA);

                menuPrincipal.setTitle("Menu principal - " + Config.NOME_SISTEMA);
                menuPrincipal.show();
                ((Stage) lbNomeDoSistema.getScene().getWindow()).close();

            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

}
