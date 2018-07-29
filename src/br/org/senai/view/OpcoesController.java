/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class OpcoesController implements Initializable {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btMenuNotificacoes;

    @FXML
    private Button btMenuStatus;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button btCentroDeCusto;

    @FXML
    private AnchorPane painel;
    @FXML
    private Button btGrupo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ImageView imageView = new ImageView("file:imagens/icones/menuNotificacao.png");

        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);

        btMenuNotificacoes.setGraphic(imageView);

        btMenuNotificacoes.setTooltip(new Tooltip("Notificações"));

        btMenuNotificacoes.setContentDisplay(ContentDisplay.TOP);

        ImageView imageView1 = new ImageView("file:imagens/icones/menuStatus.png");

        imageView1.setPreserveRatio(true);
        imageView1.setFitHeight(50);

        btMenuStatus.setGraphic(imageView1);
        btMenuStatus.setTooltip(new Tooltip("Status"));
        btMenuStatus.setContentDisplay(ContentDisplay.TOP);

        Ferramenta.configurarImagemNoBotao(btCentroDeCusto, "menuCentroDeCusto.png", "Centro de custo");

        Ferramenta.configurarImagemNoBotao(btGrupo, "menuUsuarios.png", "Grupo");

        btMenuStatus.setOnAction((ActionEvent event) -> {
            borderPane.setCenter(Dialog.load("CadastroDeStatus"));
        });

        btCancelar.setOnAction((ActionEvent event) -> {
            ((Stage) (btMenuNotificacoes.getScene().getWindow())).close();
        });

        btCentroDeCusto.setOnAction((ActionEvent event) -> {
            borderPane.setCenter(Dialog.load("CadastroDeCentrosDeCustos_1"));
        });

        btGrupo.setOnAction((ActionEvent event) -> {
            borderPane.setCenter(Dialog.load("CadastroDeGrupo"));
        });

        btMenuNotificacoes.setOnAction((ActionEvent event) -> {
            borderPane.setCenter(Dialog.load("Notificacoes"));
        });

    }

    private void addNoPainel(String arquivo) {
        painel.getChildren().clear();
        painel.getChildren().add(Dialog.load(arquivo));
    }

}
