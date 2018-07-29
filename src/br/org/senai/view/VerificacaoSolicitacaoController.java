/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.ItensSolicitacaoDAO;
import br.org.senai.dao.SolicitacaoDAO;
import br.org.senai.dao.StatusDAO;
import br.org.senai.entities.ItensSolicitacao;
import br.org.senai.entities.Solicitacao;
import br.org.senai.entities.Status;
import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class VerificacaoSolicitacaoController implements Initializable {

    @FXML
    private Label lbDataSolicitacao;

    @FXML
    private Label lbInstrutor;

    @FXML
    private Label lbDataDeNecessidade;

    @FXML
    private TableView<ItensSolicitacao> tableItens;

    @FXML
    private Button btConfirmarSolicitacao;

    @FXML
    private TableColumn<ItensSolicitacao, Image> tcImageItem;

    @FXML
    private TableColumn<ItensSolicitacao, String> tcNomeItem;

    @FXML
    private TableColumn<ItensSolicitacao, String> tcQuantidadeEmEstoque;

    @FXML
    private TableColumn<ItensSolicitacao, Integer> tcQuantidadeSolicitada;

    @FXML
    private TableColumn<ItensSolicitacao, TextField> tcQuantidadeAprovada;

    @FXML
    private ComboBox<Status> cbStatus;

    private ObservableList<ItensSolicitacao> itens = FXCollections.observableArrayList();

    private Solicitacao solicitacao = new Solicitacao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Ferramenta.fillComboBox(cbStatus, new StatusDAO().listarTodos());

        Platform.runLater(() -> {

            solicitacao = (Solicitacao) lbDataSolicitacao.getParent().getUserData();

            lbDataSolicitacao.setText(Ferramenta.formatarData(solicitacao.getDataSolicitacao()));

            lbInstrutor.setText(solicitacao.getUsuario().getNome());

            lbDataDeNecessidade.setText(Ferramenta.formatarData(solicitacao.getDataNecessidade()));

            itens.setAll(new ItensSolicitacaoDAO().getDaSolicitacao(solicitacao));

            tcImageItem.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, Image> param) -> {

                if (param.getValue().getItensSolicitacaoPK().getProduto().getImagem() == null) {
                    return new SimpleObjectProperty<>(new Image("file:imagens/icones/semFoto.jpg"));
                } else {
                    return new SimpleObjectProperty<>(new Image(new ByteArrayInputStream(param.getValue().getItensSolicitacaoPK().getProduto().getImagem())));
                }
            });

            tcImageItem.setCellFactory((TableColumn<ItensSolicitacao, Image> param) -> new TableCell<ItensSolicitacao, Image>() {

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

            tcNomeItem.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, String> param) -> new SimpleStringProperty(param.getValue().getItensSolicitacaoPK().getProduto().getNome()));

            tcNomeItem.setCellFactory((TableColumn<ItensSolicitacao, String> param) -> new TableCell<ItensSolicitacao, String>() {

                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setText(item);
                    }
                    setAlignment(Pos.CENTER);
                }

            });

            tcQuantidadeEmEstoque.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, String> param) -> new SimpleStringProperty(Integer.toString(param.getValue().getItensSolicitacaoPK().getProduto().getQuantidade())));

            tcQuantidadeEmEstoque.setCellFactory((TableColumn<ItensSolicitacao, String> param) -> new TableCell<ItensSolicitacao, String>() {

                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                    setAlignment(Pos.CENTER);
                }
            });

            tcQuantidadeSolicitada.setCellValueFactory(new PropertyValueFactory<>("quantidadeSolicitada"));

            tcQuantidadeSolicitada.setCellFactory((TableColumn<ItensSolicitacao, Integer> param) -> new TableCell<ItensSolicitacao, Integer>() {

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (empty) {
                        setText(null);
                    } else {
                        setText(Integer.toString(item));
                    }
                    setAlignment(Pos.CENTER);
                }

            });

            tcQuantidadeAprovada.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, TextField> param) -> new SimpleObjectProperty<>(new TextField()));

            tcQuantidadeAprovada.setCellFactory((TableColumn<ItensSolicitacao, TextField> param) -> new TableCell<ItensSolicitacao, TextField>() {

                @Override
                protected void updateItem(TextField item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        item.setPromptText("Digite a quantidade aprovada.");
                        item.setOnKeyReleased((KeyEvent event) -> {

                            TextField textField = (TextField) event.getSource();

                            if (!textField.getText().isEmpty()) {

                                try {
                                    int valor = Integer.parseInt(item.getText());

                                    ItensSolicitacao itensSolicitacao = ((ItensSolicitacao) ((TableRow) ((TextField) event.getSource()).getParent().getParent()).getItem());

                                    itensSolicitacao = itens.get(itens.lastIndexOf(itensSolicitacao));

                                    if (valor > itensSolicitacao.getItensSolicitacaoPK().getProduto().getQuantidade() || valor > itensSolicitacao.getItensSolicitacaoPK().getProduto().getQuantidade() || valor < 0) {
                                        textField.setText(null);
                                    } else {
                                        itensSolicitacao.setQuantidadeEntregue(valor);
                                    }

                                    atualizarStatus();
                                } catch (java.lang.NumberFormatException ex) {
                                    System.err.print(ex);
                                }

                            }
                        });
                        setGraphic(item);
                    }

                    setAlignment(Pos.CENTER);
                }
            });

            tableItens.setItems(itens);

        });

        btConfirmarSolicitacao.setOnAction((ActionEvent event) -> {

            if (Dialog.confirmation("Deseja confirmar solicitação?")) {

                solicitacao.setStatus(cbStatus.getSelectionModel().getSelectedItem());

                new SolicitacaoDAO().update(solicitacao);

                for (ItensSolicitacao iten : itens) {
                    new ItensSolicitacaoDAO().update(iten);
                }

                Dialog.information("Solicitação confirmada");

                ((Stage) btConfirmarSolicitacao.getScene().getWindow()).close();
            }
        });

    }

    private void atualizarStatus() {
        for (ItensSolicitacao itemSolicitacao : itens) {
            if (itemSolicitacao.getQuantidadeSolicitada() != itemSolicitacao.getQuantidadeEntregue()) {
                cbStatus.setValue(StatusDAO.aprovadoParcialmente);
            } else {
                solicitacao.setAprovadoSupervisao(true);
                cbStatus.setValue(StatusDAO.aprovadoSupervisao);
            }
            cbStatus.managedProperty().bind(cbStatus.visibleProperty());
        }
    }
}
