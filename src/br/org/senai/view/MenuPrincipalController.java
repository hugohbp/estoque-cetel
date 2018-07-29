/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import br.org.senai.util.Notificacao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    private AnchorPane painelConteudo;

    @FXML
    private AnchorPane painelPrincipal;

    @FXML
    private StackPane stackPane;

    @FXML
    private Button btSolicitaItens;

    @FXML
    private Button btProdutos;

    @FXML
    private Button btEstoque;

    @FXML
    private Button btCompra;

    @FXML
    private ImageView imgUsuario;

    @FXML
    private MenuItem miSair;

    @FXML
    private Label lbUsuarioLogado;

    @FXML
    private Label lbSair;

    @FXML
    private Button btSolicitacoes;

    @FXML
    private Button btInicio;

    @FXML
    private Button btUsuario;

    @FXML
    private MenuItem miOpcoes;

    @FXML
    private MenuItem miSobre;

    @FXML
    private Label lbNumeroDeNotificacoes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        Notificacao notificacao = new Notificacao();
//
//        notificacao.executar();

        imgUsuario.setOnMouseClicked((MouseEvent event) -> {
            Dialog.abrirJanela("Perfil", "Alterar perfil", true, false, false);
        });

        addInicio();

        lbNumeroDeNotificacoes.setVisible(false);

        lbUsuarioLogado.setText(Config.usuarioLogado.getNome());

        lbSair.setOnMouseClicked((MouseEvent event) -> {
            sairDoSistema();
        });

        miSair.setOnAction((ActionEvent event) -> {
            sairDoSistema();
        });

        ImageView imageView = new ImageView(new Image("file:imagens/icones/menuSolicitacao.png"));
        imageView.setPreserveRatio(true);

        imageView.setFitHeight(50);

        btSolicitaItens.setGraphic(imageView);

        btSolicitaItens.setTooltip(new Tooltip("Solicitar itens"));

        btSolicitaItens.setText("Solicitar itens");
        btSolicitaItens.setContentDisplay(ContentDisplay.TOP);

        btSolicitaItens.setOnAction((ActionEvent event) -> {
            painelConteudo.getChildren().clear();

            try {
                painelConteudo.getChildren().add(FXMLLoader.load(getClass().getResource("/br/org/senai/view/CadastroDeSolicitacoes_1.fxml")));
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ImageView imageView1 = new ImageView(new Image("file:imagens/icones/menuProduto.png"));
        imageView1.setPreserveRatio(true);
        imageView1.setFitHeight(50);

        btProdutos.setGraphic(imageView1);
        btProdutos.setTooltip(new Tooltip("Produtos"));
        btProdutos.setText("Produtos");
        btProdutos.setContentDisplay(ContentDisplay.TOP);
        btProdutos.setOnAction((ActionEvent event) -> {
            painelConteudo.getChildren().clear();
            try {
                painelConteudo.getChildren().add(FXMLLoader.load(getClass().getResource("/br/org/senai/view/CadastroDeProdutos.fxml")));
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ImageView imageView2 = new ImageView(new Image("file:imagens/icones/menuEstoque.png"));
        imageView2.setPreserveRatio(true);

        imageView2.setFitHeight(50);

        btEstoque.setGraphic(imageView2);
        btEstoque.setTooltip(new Tooltip("Estoque"));
        btEstoque.setText("Estoque");
        btEstoque.setContentDisplay(ContentDisplay.TOP);

        btCompra.setVisible(false);
        btCompra.managedProperty().bind(btCompra.visibleProperty());
        ImageView imageView3 = new ImageView(new Image("file:imagens/icones/menuCompra.png"));
        imageView3.setPreserveRatio(true);
        imageView3.setFitHeight(50);

        btCompra.setGraphic(imageView3);
        btCompra.setTooltip(new Tooltip("Compras"));
        btCompra.setText("Compras");

        btCompra.setContentDisplay(ContentDisplay.TOP);

        if (Config.usuarioLogado.getFoto() == null) {
            imgUsuario.setImage(Config.ICONE_SEM_IMAGEM);
        } else {
            imgUsuario.setImage(Ferramenta.converterByteParaImage(Config.usuarioLogado.getFoto()));
        }

        btEstoque.setOnAction((ActionEvent event) -> {
            addNoPainel("Estoque");
        });

        ImageView imageView4 = new ImageView("file:imagens/icones/menuSolicitacoes.png");
        imageView4.setPreserveRatio(true);
        imageView4.setFitHeight(50);

        btSolicitacoes.setGraphic(imageView4);
        btSolicitacoes.setTooltip(new Tooltip("Solicitações"));
        btSolicitacoes.setText("Solicitações");
        btSolicitacoes.setContentDisplay(ContentDisplay.TOP);

        btSolicitacoes.setOnAction((ActionEvent event) -> {
            addNoPainel("SolicitacoesInstrutor");
        });

        ImageView imageView5 = new ImageView("file:imagens/icones/menuHome1.png");
        imageView5.setPreserveRatio(true);
        imageView5.setFitHeight(50);

        btInicio.setGraphic(imageView5);
        btInicio.setTooltip(new Tooltip("Início"));
        btInicio.setText("Início");
        btInicio.setContentDisplay(ContentDisplay.TOP);

        btInicio.setOnAction((ActionEvent event) -> {
            addInicio();
        });

        ImageView imageView6 = new ImageView("file:imagens/icones/menuUsuarios.png");
        imageView6.setPreserveRatio(true);
        imageView6.setFitHeight(50);

        btUsuario.setGraphic(imageView6);
        btUsuario.setTooltip(new Tooltip("Usuários"));
        btUsuario.setText("Usuários");
        btUsuario.setContentDisplay(ContentDisplay.TOP);

        btUsuario.setOnAction((ActionEvent event) -> {
            addNoPainel("CadastroDeUsuarios");
        });

        miOpcoes.setOnAction((ActionEvent event) -> {
            Dialog.abrirJanela("Opcoes", "Opções", true, false, false);
        });

        miSobre.setOnAction((ActionEvent event) -> {
            Dialog.abrirJanela("Sobre", "Sobre", true, false, false);
        });

        lbUsuarioLogado.setTooltip(new Tooltip("Clique para alterar o perfil"));
        lbSair.setTooltip(new Tooltip("Clique aqui para sair."));

        lbUsuarioLogado.setOnMouseClicked((MouseEvent event) -> {
            Dialog.abrirJanela("Perfil", "Alterar perfil", true, false, false);
        });

        Platform.runLater(() -> {
            ((Stage) lbUsuarioLogado.getScene().getWindow()).setOnCloseRequest((WindowEvent event) -> {
                if (Dialog.confirmation("Deseja sair do sistema?")) {
                    ((Stage) lbUsuarioLogado.getScene().getWindow()).close();
                    Dialog.abrirJanela("Login", "Login", true, false, false);
                } else {
                    event.consume();
                }
            });
        });
    }

    private void addInicio() {

        switch (Config.usuarioLogado.getGrupo().getId()) {
            case 1:
                addNoPainel("InicioSupervisor");
                break;
            case 2:

                break;
            case 8:
                addNoPainel("InicioManutencao");
                break;

        }
    }

    private void carregarNotificacoes() {

    }

    private void sairDoSistema() {
        if (Dialog.confirmation("Deseja sair do sistema?")) {
            ((Stage) (btSolicitaItens.getScene().getWindow())).close();
            Dialog.abrirJanela("Login", "Login", true, false, false, true);
        }
    }

    private void addNoPainel(String arquivo) {

        painelConteudo.getChildren().clear();
        try {
            painelConteudo.getChildren().add(FXMLLoader.load(getClass().getResource("/br/org/senai/view/" + arquivo + ".fxml")));
        } catch (IOException ex) {
            Dialog.error(ex.getMessage());
        }

    }

}
