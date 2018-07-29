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
import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
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
public class InicioManutencaoController implements Initializable {

    @FXML
    private TableView<Solicitacao> tableView;
    @FXML
    private TableColumn<Solicitacao, String> tcDataDeNecessidade;
    @FXML
    private TableColumn<Solicitacao, String> tcItens;
    @FXML
    private TableColumn<Solicitacao, String> tcInstrutor;
    private ObservableList<Solicitacao> solicitacoes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcDataDeNecessidade.setCellValueFactory((TableColumn.CellDataFeatures<Solicitacao, String> param) -> new SimpleObjectProperty<>(Ferramenta.formatarData(param.getValue().getDataNecessidade())));
        tcItens.setCellValueFactory((TableColumn.CellDataFeatures<Solicitacao, String> param) -> {
            String texto = "";
            for (ItensSolicitacao itensSolicitacao : new ItensSolicitacaoDAO().getDaSolicitacao(param.getValue())) {
                texto += itensSolicitacao.getItensSolicitacaoPK().getProduto().getNome() + "; ";
            }
            return new SimpleObjectProperty<>(texto);
        });
        tcInstrutor.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        solicitacoes.setAll(new SolicitacaoDAO().pegarSolicitacoesManutencao());
        tableView.setItems(solicitacoes);
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Solicitacao solicitacao = tableView.getSelectionModel().getSelectedItem();
                if (solicitacao != null) {
                    Dialog.abrirJanela("DetalhesSolicitacaoAlmoxarife", "Detalhes", true, false, false, solicitacao);
                    solicitacoes.setAll(new SolicitacaoDAO().pegarSolicitacoesManutencao());
                    tableView.setItems(solicitacoes);
                }
            }
        });

    }

}
