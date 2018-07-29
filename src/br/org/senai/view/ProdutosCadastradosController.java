/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.ProdutoDAO;
import br.org.senai.entities.Produto;
import br.org.senai.util.Dialog;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class ProdutosCadastradosController implements Initializable {

    @FXML
    private TableView<Produto> tableView;

    @FXML
    private TableColumn<Produto, String> nomeProduto;

    @FXML
    private TableColumn<Produto, Integer> quantidadeProduto;

    @FXML
    private TableColumn<Produto, Image> imgProduto;

    @FXML
    private TextField tfPesquisaNome;

    private ObservableList<Produto> produtos = FXCollections.observableArrayList();

    @FXML
    private Button btEditarProduto;

    @FXML
    private Button btExcluirProduto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ImageView imageView = new ImageView("file:imagens/icones/editar.png");
        
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(25d);
        
        btEditarProduto.setGraphic(imageView);
        
        btEditarProduto.setAlignment(Pos.CENTER);
        
        ImageView imageView1 = new ImageView("file:imagens/icones/excluir.png");
        
        imageView1.setPreserveRatio(true);
        imageView1.setFitHeight(25d);
        
        btExcluirProduto.setGraphic(imageView1);
        btExcluirProduto.setAlignment(Pos.CENTER);
        
        

        produtos.setAll(new ProdutoDAO().listarTodos());
        nomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        quantidadeProduto.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        imgProduto.setCellValueFactory((TableColumn.CellDataFeatures<Produto, Image> param) -> {
            try {
                if (param.getValue().getImagem() == null) {
                    return new SimpleObjectProperty<>(new Image("file:imagens/icones/semFoto.jpg"));
                } else {
                    return new SimpleObjectProperty<>(new Image(new ByteArrayInputStream(param.getValue().getImagem())));
                }
            } catch (NullPointerException ex) {
                return new SimpleObjectProperty<>(new Image("file:imagens/icones/semFoto.jpg"));
            }
        });

        imgProduto.setCellFactory((TableColumn<Produto, Image> param) -> new TableCell<Produto, Image>() {

            @Override
            protected void updateItem(Image item, boolean empty) {
                if (empty) {
                    setGraphic(null);

                } else {

                    ImageView imageView = new ImageView(item);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(75d);
                    setGraphic(imageView);

                }
                setAlignment(Pos.CENTER);
            }

        });
        tableView.setItems(produtos);

        tableView.setOnMouseClicked((MouseEvent event) -> {

        });

        tfPesquisaNome.setOnKeyReleased((KeyEvent event) -> {
            Platform.runLater(() -> {
                produtos.setAll(new ProdutoDAO().getProdutoPorNome(tfPesquisaNome.getText()));
            });
        });

        btExcluirProduto.setOnAction((ActionEvent event) -> {

            Produto produto = tableView.getSelectionModel().getSelectedItem();

            if (produto != null) {
                if (Dialog.confirmation("Deseja excluir o produto?\nProduto: " + produto.getNome() + "\nQuantidade disponível: " + produto.getQuantidade())) {
                    new ProdutoDAO().delete(produto);
                    Dialog.information("Produto apagado com sucesso.");
                    Platform.runLater(() -> {
                        produtos.setAll(new ProdutoDAO().getProdutoPorNome(tfPesquisaNome.getText()));
                    });
                }
            } else {
                Dialog.information("Selecione um produto para excluir.");
            }

        });

        btEditarProduto.setOnAction((ActionEvent event) -> {

            if (tableView.getSelectionModel().getSelectedItem() != null) {
                Dialog.abrirJanela("CadastroDeProdutos1", "Editar produto", true, false, false, tableView.getSelectionModel().getSelectedItem());
                produtos.setAll(new ProdutoDAO().listarTodos());
            } else {

            }

        });
    }

}
