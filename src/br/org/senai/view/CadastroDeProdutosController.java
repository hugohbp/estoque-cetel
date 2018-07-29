/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CadastroDeProdutosController implements Initializable {

   
    @FXML
    private AnchorPane apProdutosCadastrados;

    @FXML
    private Tab tProdutosCadastrados;

    @FXML
    private AnchorPane apCadastroDeProdutos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        

//        pane.setOnMouseClicked((MouseEvent event) -> {
//
//            switch (pane.getSelectionModel().getSelectedItem().getText()) {
//                case "Cadastro de Produtos":
//
//                    break;
//                case "Produtos cadastrados": {
//                    try {
//                        System.out.println("clicou na guia");
//                        apProdutosCadastrados.getChildren().add(FXMLLoader.load(getClass().getResource("/br/org/senai/view/ProdutosCadastrados.fxml")));
//                    } catch (IOException ex) {
//                        Logger.getLogger(CadastroDeProdutosController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                break;
//
//            }
//        });
        tProdutosCadastrados.setOnCloseRequest((Event event) -> {
        });

        tProdutosCadastrados.setOnSelectionChanged((Event event) -> {

            System.out.println("clicou na guia");

            try {
                apProdutosCadastrados.getChildren().clear();
                apProdutosCadastrados.getChildren().add(FXMLLoader.load(getClass().getResource("/br/org/senai/view/ProdutosCadastrados.fxml")));
            } catch (IOException ex) {
                Logger.getLogger(CadastroDeProdutosController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        apCadastroDeProdutos.getChildren().clear();
        try {
            apCadastroDeProdutos.getChildren().add(FXMLLoader.load(getClass().getResource("/br/org/senai/view/CadastroDeProdutos1.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(CadastroDeProdutosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   

}
