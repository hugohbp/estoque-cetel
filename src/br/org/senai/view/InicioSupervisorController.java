/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.ItensSolicitacaoDAO;
import br.org.senai.dao.SolicitacaoDAO;
import br.org.senai.entities.ItensSolicitacao;
import br.org.senai.entities.Solicitacao;
import br.org.senai.util.Dialog;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class InicioSupervisorController implements Initializable {

    @FXML
    private TextArea taBemVindo;

    @FXML
    private TableView<Solicitacao> tableSolicitacoes;

    @FXML
    private TableColumn<Solicitacao, String> tcDataSolicitacao;

    @FXML
    private TableColumn<Solicitacao, String> tcItens;

    @FXML
    private TableColumn<Solicitacao, String> tcInstrutor;

    @FXML
    private TableColumn<Solicitacao, String> tcSituacao;

    @FXML
    private TextArea taNotificacao;

    private String itens = "";

    private ObservableList<Solicitacao> solicitacoes = FXCollections.observableArrayList();

    private int countCliks;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        taBemVindo.setEditable(false);
        taBemVindo.setText("Seja bem vindo ao Estoque CETEL! \nVersão: 0.2");

        taNotificacao.setEditable(false);
        taNotificacao.setText("Você não possui notificações no momento.");

        tcDataSolicitacao.setCellValueFactory((TableColumn.CellDataFeatures<Solicitacao, String> param) -> new SimpleObjectProperty<>(new SimpleDateFormat("dd-MM-yyyy").format(param.getValue().getDataSolicitacao())));

        for (Solicitacao solicitacao : new SolicitacaoDAO().pegarSolicitacoesInstrutor()) {
            for (ItensSolicitacao item : new ItensSolicitacaoDAO().getDaSolicitacao(solicitacao)) {
                itens += item.getItensSolicitacaoPK().getProduto().getNome() + "; ";
            }
        }

        tcItens.setCellValueFactory((TableColumn.CellDataFeatures<Solicitacao, String> param) -> new SimpleObjectProperty<>(itens));

        tcInstrutor.setCellValueFactory((TableColumn.CellDataFeatures<Solicitacao, String> param) -> new SimpleObjectProperty<>(param.getValue().getUsuario().getNome()));

        tcSituacao.setCellValueFactory((TableColumn.CellDataFeatures<Solicitacao, String> param) -> new SimpleObjectProperty<>(param.getValue().getStatus().getDescricao()));

        atualizarTabela();
        tableSolicitacoes.setOnMouseClicked((MouseEvent event) -> {

            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {

                if (tableSolicitacoes.getSelectionModel().getSelectedItem() != null) {
                    Dialog.abrirJanela("VerificacaoSolicitacao", "Confirmar solicitação", true, false, false, tableSolicitacoes.getSelectionModel().getSelectedItem());
                }
                tableSolicitacoes.getSelectionModel().clearSelection();
                atualizarTabela();

            }

        });
    }

    private void atualizarTabela() {
        solicitacoes.setAll(new SolicitacaoDAO().pegarSolicitacoesInstrutor());
//        Platform.runLater(() -> {
//            Dialog.notificationInformation(null, solicitacoes.size() + " solicitações não aprovadas.");
//        });       
        tableSolicitacoes.setItems(solicitacoes);
    }

}
