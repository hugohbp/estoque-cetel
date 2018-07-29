/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.StatusDAO;
import br.org.senai.entities.Status;
import br.org.senai.util.Dialog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CadastroDeStatusController implements Initializable {
    
    @FXML
    private TextField tfStatus;
    
    @FXML
    private Button btCadastrar;
    
    @FXML
    private Button btRemover;
    
    @FXML
    private Button btCancelar;
    
    private ObservableList<Status> listStatus = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Status> tableView;
    
    @FXML
    private TableColumn<Status, String> tcStatus;
    
    private Status status;
    
    private boolean editando = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listStatus.clear();
        
        tcStatus.setCellValueFactory((TableColumn.CellDataFeatures<Status, String> param) -> {
            return new SimpleObjectProperty<>(param.getValue().getDescricao());
        });
        
        atualizarLista();
        
        status = null;
        
        editando = false;
        
        tfStatus.setText(null);
        
        btCadastrar.setText("Salvar");
        
        tfStatus.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                btCadastrar.getOnAction().handle(new ActionEvent());
            }
        });
        
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    editando = true;
                    btCadastrar.setText("Salvar alterações");
                    status = tableView.getSelectionModel().getSelectedItem();
                    tfStatus.setText(status.getDescricao());
                }
            }
        });
        
        btCadastrar.setOnAction((ActionEvent event) -> {
            if (!tfStatus.getText().isEmpty()) {
                
                if (editando) {
                    status.setDescricao(tfStatus.getText());
                    new StatusDAO().update(status);
                    Dialog.information("Status alterado com sucesso.");
                    
                } else {
                    status = new Status();
                    status.setDescricao(tfStatus.getText());
                    new StatusDAO().save(status);
                    Dialog.information("Status salvo com sucesso.");
                }
                initialize(url, rb);
            } else {
                Dialog.information("Preencha o campo corretamente.");
            }
        });
        
        btRemover.setOnAction((ActionEvent event) -> {
            status = tableView.getSelectionModel().getSelectedItem();
            if (Dialog.confirmation("Deseja remover o status: " + status.getDescricao() + " ? \n *Todas as informações relacionadas a esse status SERÃO REMOVIDAS.")) {
                new StatusDAO().delete(status);
                Dialog.information("Status apagado com sucesso.");
            }
            atualizarLista();
            initialize(url, rb);
        });
        
        btCancelar.setOnAction((ActionEvent event) -> {
            editando = false;
            btCadastrar.setText("Salvar");
            status = null;
           tfStatus.setText(null);
        });
        
    }
    
    private void atualizarLista() {
        listStatus.setAll(new StatusDAO().listarTodos());
        tableView.setItems(listStatus);
    }
    
}
