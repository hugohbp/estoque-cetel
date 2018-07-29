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
import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class DetalhesSolicitacaoController implements Initializable {
    
    @FXML
    private Label lbDataDeSolicitacao;
    @FXML
    private Label lbDataDeNecessidade;
    @FXML
    private Label lbStatus;
    @FXML
    private Button btCancelarSolicitacao;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btAprovarSolicitacao;
    @FXML
    private TableView<ItensSolicitacao> tableView;
    @FXML
    private TableColumn<ItensSolicitacao, StackPane> tcProduto;
    @FXML
    private TableColumn<ItensSolicitacao, String> tcQuantidadeSolicitada;
    @FXML
    private TableColumn<ItensSolicitacao, String> tcQuantidadeAprovada;
    private ObservableList<ItensSolicitacao> itensSolicitacao = FXCollections.observableArrayList();
    private Solicitacao solicitacao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Platform.runLater(() -> {
            solicitacao = (Solicitacao) lbDataDeSolicitacao.getParent().getUserData();
            lbDataDeSolicitacao.setText(Ferramenta.formatarData(solicitacao.getDataSolicitacao()));
            lbDataDeNecessidade.setText(Ferramenta.formatarData(solicitacao.getDataNecessidade()));
            lbStatus.setText(solicitacao.getStatus().getDescricao());
            itensSolicitacao.setAll(new ItensSolicitacaoDAO().getDaSolicitacao(solicitacao));
            
            if (!solicitacao.getStatus().equals(StatusDAO.aprovadoParcialmente)) {
                btAprovarSolicitacao.setVisible(false);
                btAprovarSolicitacao.managedProperty().bind(btAprovarSolicitacao.visibleProperty());
            }
            if (solicitacao.getStatus().equals(StatusDAO.fechado)) {
                btCancelarSolicitacao.setVisible(false);
                btCancelarSolicitacao.managedProperty().bind(btCancelarSolicitacao.visibleProperty());
            }
            tcProduto.setCellFactory(new Callback<TableColumn<ItensSolicitacao, StackPane>, TableCell<ItensSolicitacao, StackPane>>() {
                
                @Override
                public TableCell<ItensSolicitacao, StackPane> call(TableColumn<ItensSolicitacao, StackPane> param) {
                    return new TableCell<ItensSolicitacao, StackPane>() {
                        
                        @Override
                        protected void updateItem(StackPane item, boolean empty) {
                            if (empty) {
                                setGraphic(null);
                            } else {
                                StackPane.setAlignment(item, Pos.CENTER);
                                setGraphic(item);
                            }
                            setAlignment(Pos.CENTER);
                        }
                    };
                }
            });
            tcProduto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItensSolicitacao, StackPane>, ObservableValue<StackPane>>() {
                @Override
                public ObservableValue<StackPane> call(TableColumn.CellDataFeatures<ItensSolicitacao, StackPane> param) {
                    
                    VBox vBox = new VBox(7);
                    vBox.setAlignment(Pos.CENTER);
                    vBox.managedProperty().bind(vBox.visibleProperty());
                    ImageView imageView = new ImageView();
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(75d);
                    if (param.getValue().getItensSolicitacaoPK().getProduto().getImagem() == null) {
                        imageView.setImage(Config.ICONE_SEM_IMAGEM);
                    } else {
                        imageView.setImage(new Image(new ByteArrayInputStream(param.getValue().getItensSolicitacaoPK().getProduto().getImagem())));
                    }
                    vBox.getChildren().add(imageView);
                    vBox.getChildren().add(new Label(param.getValue().getItensSolicitacaoPK().getProduto().getNome()));
                    StackPane stackPane = new StackPane(vBox);
                    return new SimpleObjectProperty<>(stackPane);
                }
            });
            tcQuantidadeAprovada.setCellFactory((TableColumn<ItensSolicitacao, String> param) -> {
                return new TableCell<ItensSolicitacao, String>() {
                    
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new Label(item));
                        }
                        setAlignment(Pos.CENTER);
                    }
                };
            });
            tcQuantidadeAprovada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItensSolicitacao, String>, ObservableValue<String>>() {
                
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ItensSolicitacao, String> param) {
                    return new SimpleStringProperty(Integer.toString(param.getValue().getQuantidadeEntregue()));
                }
            });
            tcQuantidadeSolicitada.setCellFactory(new Callback<TableColumn<ItensSolicitacao, String>, TableCell<ItensSolicitacao, String>>() {
                
                @Override
                public TableCell<ItensSolicitacao, String> call(TableColumn<ItensSolicitacao, String> param) {
                    return new TableCell<ItensSolicitacao, String>() {
                        
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(new Label(item));
                            }
                            setAlignment(Pos.CENTER);
                        }
                        
                    };
                }
            });
            tcQuantidadeSolicitada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItensSolicitacao, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ItensSolicitacao, String> param) {
                    return new SimpleStringProperty(Integer.toString(param.getValue().getQuantidadeSolicitada()));
                }
            });
            
            tableView.setItems(itensSolicitacao);
            btCancelar.setOnAction((ActionEvent event) -> {
                ((Stage) lbDataDeSolicitacao.getScene().getWindow()).close();
            });
            btCancelarSolicitacao.setOnAction((ActionEvent event) -> {
                if (Dialog.confirmation("Deseja realmente cancelar sua solicitação?")) {
                    solicitacao.setCancelada(true);
                    solicitacao.setStatus(StatusDAO.fechado);
                    new SolicitacaoDAO().update(solicitacao);
                    Dialog.information("Solicitação cancelada");
                    btCancelar.getOnAction().handle(event);
                }
            });
            btAprovarSolicitacao.setOnAction((ActionEvent event) -> {
                if (Dialog.confirmation("Deseja aprovar sua solicitação?")) {
                    solicitacao.setStatus(StatusDAO.aprovadoSupervisao);
                    solicitacao.setAprovadoSupervisao(true);
                    new SolicitacaoDAO().update(solicitacao);
                    Dialog.information("Solicitação aprovada.\nAguarde a resposta da manutenção.");
                    btCancelar.getOnAction().handle(event);
                }
            });
        });
        
    }
}
