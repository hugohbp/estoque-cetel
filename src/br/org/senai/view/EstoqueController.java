/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.ProdutoDAO;
import br.org.senai.entities.Produto;
import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class EstoqueController implements Initializable {
    
    @FXML
    private TableView<Produto> tableView;
    
    @FXML
    private TableColumn<Produto, StackPane> tcProduto;
    
    @FXML
    private Label lbNomeProduto;
    
    @FXML
    private TextField tfQuantidadeMinima;
    
    @FXML
    private TextField tfQuantidade;
    
    @FXML
    private ImageView ivProduto;
    
    @FXML
    private Button btSalvarAlteracoes;
    
    private Produto produtoSelecionado;
    
    ObservableList<Produto> produtos = FXCollections.observableArrayList();
    
    @FXML
    private Pane pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        pane.setVisible(false);
        pane.managedProperty().bind(pane.visibleProperty());
        produtos.setAll(new ProdutoDAO().listarTodos());
        
        tcProduto.setCellFactory((TableColumn<Produto, StackPane> param) -> new TableCell<Produto, StackPane>() {
            @Override
            protected void updateItem(StackPane item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    StackPane.setAlignment(item, Pos.CENTER);
                    setGraphic(item);
                }
            }
        });
        tcProduto.setCellValueFactory((TableColumn.CellDataFeatures<Produto, StackPane> param) -> {
            VBox vBox = new VBox(5);
            vBox.setAlignment(Pos.CENTER);
            vBox.managedProperty().bind(vBox.visibleProperty());
            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(75d);
            if (param.getValue().getImagem() == null) {
                imageView.setImage(Config.ICONE_SEM_IMAGEM);
            } else {
                imageView.setImage(new Image(new ByteArrayInputStream(param.getValue().getImagem())));
            }
            vBox.getChildren().add(imageView);
            Label label = new Label(param.getValue().getNome());
            label.setFont(Font.font(null, FontWeight.BOLD, 12));
            vBox.getChildren().add(label);
            StackPane stackPane = new StackPane(vBox);
            return new SimpleObjectProperty<>(stackPane);
            
        });
        tableView.setItems(produtos);
        
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (tableView.getSelectionModel().getSelectedItem() instanceof Produto) {
                pane.setVisible(true);
                pane.managedProperty().bind(pane.visibleProperty());
                produtoSelecionado = tableView.getSelectionModel().getSelectedItem();
                
                lbNomeProduto.setText(produtoSelecionado.getNome());
                lbNomeProduto.setTooltip(new Tooltip(produtoSelecionado.getDescricao()));
                
                if (produtoSelecionado.getImagem() != null) {
                    ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(produtoSelecionado.getImagem())));
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(true);
                    
                    ivProduto.setImage(imageView.getImage());
                } else {
                    ivProduto.setImage(new Image("file:imagens/icones/semFoto.jpg"));
                }
                tfQuantidadeMinima.setText(Integer.toString(produtoSelecionado.getQuantidadeMinima()));
                tfQuantidade.setText(Integer.toString(produtoSelecionado.getQuantidade()));
            }
        });
        
        btSalvarAlteracoes.setOnAction((ActionEvent event) -> {
            if (produtoSelecionado == null) {
                Dialog.information("Selecione um produto.");
            } else {
                if (Dialog.confirmation("Deseja salvar as alterações para o produto : " + produtoSelecionado.getNome() + "?")) {
                    produtoSelecionado.setQuantidade(Integer.parseInt(tfQuantidade.getText()));
                    produtoSelecionado.setQuantidadeMinima(Integer.parseInt(tfQuantidadeMinima.getText()));
                    new ProdutoDAO().update(produtoSelecionado);
                    Dialog.information("Produto atulizado com sucesso!");
                }
            }
        });
    }
    
}
