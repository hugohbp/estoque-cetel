/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.ItensSolicitacaoDAO;
import br.org.senai.dao.ProdutoDAO;
import br.org.senai.dao.SolicitacaoDAO;
import br.org.senai.dao.StatusDAO;
import br.org.senai.entities.ItensSolicitacao;
import br.org.senai.entities.Produto;
import br.org.senai.entities.Solicitacao;
import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class DetalhesSolicitacaoAlmoxarifeController implements Initializable {

    @FXML
    private Label lbInstrutor;
    @FXML
    private Label lbDataNecessidade;
    @FXML
    private Label lbRealizado;
    @FXML
    private Button btAtualizar;
    @FXML
    private Button btConcluirSolicitacao;
    @FXML
    private Button btCancelar;
    @FXML
    private TableView<ItensSolicitacao> tableView;
    @FXML
    private TableColumn<ItensSolicitacao, StackPane> tcProduto;
    @FXML
    private TableColumn<ItensSolicitacao, Label> tcQuantidade;
    @FXML
    private TableColumn<ItensSolicitacao, CheckBox> tcDevolucao;
    private ObservableList<ItensSolicitacao> itensSolicitacao = FXCollections.observableArrayList();
    private Solicitacao solicitacao;
    private ItensSolicitacao itemSolicitado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(() -> {
            solicitacao = (Solicitacao) lbInstrutor.getParent().getUserData();
            itensSolicitacao.setAll(new ItensSolicitacaoDAO().getDaSolicitacao(solicitacao));
            tableView.setItems(itensSolicitacao);
            lbInstrutor.setText(solicitacao.getUsuario().getNome());
            lbDataNecessidade.setText(Ferramenta.formatarData(solicitacao.getDataNecessidade()));
            long diff = new Date().getTime() - solicitacao.getDataSolicitacao().getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays != 0) {
                lbRealizado.setText("Realizado há " + diffDays + " dia(s).");
            } else {
                lbRealizado.setText("Realizado há hora(s).");
            }

            tcProduto.setCellFactory((TableColumn<ItensSolicitacao, StackPane> param) -> new TableCell<ItensSolicitacao, StackPane>() {
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
            tcProduto.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, StackPane> param) -> {
                VBox vBox = new VBox(5);
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
                Label label = new Label(param.getValue().getItensSolicitacaoPK().getProduto().getNome());
                label.setFont(Font.font(null, FontWeight.BOLD, 12));
                vBox.getChildren().add(label);
                StackPane stackPane = new StackPane(vBox);
                return new SimpleObjectProperty<>(stackPane);

            });
            tcQuantidade.setCellFactory((TableColumn<ItensSolicitacao, Label> param) -> new TableCell<ItensSolicitacao, Label>() {
                @Override
                protected void updateItem(Label item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(item);
                    }
                    setAlignment(Pos.CENTER);
                }
            });
            tcQuantidade.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, Label> param) -> new SimpleObjectProperty<>(new Label(Integer.toString(param.getValue().getQuantidadeEntregue()))));
            tcDevolucao.setCellFactory((TableColumn<ItensSolicitacao, CheckBox> param) -> new TableCell<ItensSolicitacao, CheckBox>() {

                @Override
                protected void updateItem(CheckBox item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(item);

                        item.setOnAction((ActionEvent event) -> {
                            try {
                                itemSolicitado = ((ItensSolicitacao) ((TableRow) item.getParent().getParent()).getItem());
                                itemSolicitado = itensSolicitacao.get(itensSolicitacao.lastIndexOf(itemSolicitado));
                            } catch (NullPointerException ex) {

                            }

                            if (itemSolicitado.getDataDevolucao() == null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(new Date());
                                calendar.add(Calendar.DATE, +7);
                                itemSolicitado.setDataDevolucao(calendar.getTime());
                                item.setSelected(true);
                            } else {
                                itemSolicitado.setDataDevolucao(null);
                                item.setSelected(false);
                            }
                        });
                        item.managedProperty().bind(item.visibleProperty());
                    }
                    setAlignment(Pos.CENTER);
                }
            });
            tcDevolucao.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, CheckBox> param) -> new SimpleObjectProperty<CheckBox>(new MeuCheckBox(param.getValue().getItensSolicitacaoPK().getProduto().getNome(), param.getValue().getDataDevolucao() != null)));

            btAtualizar.setOnAction((ActionEvent event) -> {
//                solicitacao.setStatus(StatusDAO.emAndamento);
//                new SolicitacaoDAO().update(solicitacao);
                for (ItensSolicitacao itensSolicitacao : itensSolicitacao) {
                    new ItensSolicitacaoDAO().update(itensSolicitacao);
                }

                Dialog.information("Solicitação atualizada");

            });

            btConcluirSolicitacao.setOnAction((ActionEvent event) -> {
                if (Dialog.confirmation("Deseja concluir sua solicitação?")) {

                    for (ItensSolicitacao itensSolicitacao : itensSolicitacao) {
                        Produto produto = itensSolicitacao.getItensSolicitacaoPK().getProduto();
                        produto.setQuantidade(produto.getQuantidade()-itensSolicitacao.getQuantidadeEntregue());
                        new ProdutoDAO().update(produto);
                        new ItensSolicitacaoDAO().update(itensSolicitacao);
                    }
                    solicitacao.setStatus(StatusDAO.disponivelParaRetirada);
                    new SolicitacaoDAO().update(solicitacao);
                }
                btCancelar.getOnAction().handle(event);
            });

            btCancelar.setOnAction((ActionEvent event) -> {
                ((Stage) lbInstrutor.getParent().getScene().getWindow()).close();
            });

        });
    }

    class MeuCheckBox extends CheckBox {

        public MeuCheckBox(String tooltip, boolean selected) {
            setTooltip(new Tooltip(tooltip));
            setSelected(selected);
            managedProperty().bind(visibleProperty());
        }

    }

}
