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
import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class SolicitacoesInstrutorController implements Initializable {

    @FXML
    private TableView<Solicitacao> tableView;

    @FXML
    private TableColumn<Solicitacao, String> tcData;

    @FXML
    private TableColumn<Solicitacao, String> tcDataNecessidade;

    @FXML
    private TableColumn<Solicitacao, String> tcItens;

    @FXML
    private TableColumn<Solicitacao, String> tcSituacao;

    private ObservableList<Solicitacao> solicitacoes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        solicitacoes.clear();

        tcData.setCellValueFactory((TableColumn.CellDataFeatures<Solicitacao, String> param) -> {
            return new SimpleStringProperty(Ferramenta.formatarData(param.getValue().getDataSolicitacao()));
        });

        tcDataNecessidade.setCellValueFactory((TableColumn.CellDataFeatures<Solicitacao, String> param) -> {
            return new SimpleStringProperty(Ferramenta.formatarData(param.getValue().getDataNecessidade()));
        });

        tcItens.setCellValueFactory((TableColumn.CellDataFeatures<Solicitacao, String> param) -> {
            String itens = "";
            for (ItensSolicitacao itensSolicitacao : new ItensSolicitacaoDAO().getDaSolicitacao(param.getValue())) {
                itens += itensSolicitacao.getItensSolicitacaoPK().getProduto().getNome() + "; ";
            }
            return new SimpleStringProperty(itens);
        });

        tcSituacao.setCellValueFactory(new PropertyValueFactory<>("status"));

        solicitacoes.setAll(new SolicitacaoDAO().pegarSolicitacaoDoUsuario(Config.usuarioLogado));

        tableView.setItems(solicitacoes);

        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Solicitacao solicitacao = tableView.getSelectionModel().getSelectedItem();
                if (solicitacao != null) {
                    Dialog.abrirJanela("DetalhesSolicitacao", "Detalhes da solicitação " + Ferramenta.formatarData(solicitacao.getDataSolicitacao()), true, false, true, solicitacao);
                    solicitacoes.setAll(new SolicitacaoDAO().pegarSolicitacaoDoUsuario(Config.usuarioLogado));
                }

            }
        });

    }

}
