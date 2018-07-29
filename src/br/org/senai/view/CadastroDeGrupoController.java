/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.GrupoDAO;
import br.org.senai.entities.Grupo;
import br.org.senai.util.Dialog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CadastroDeGrupoController implements Initializable {
    
    @FXML
    private TableView<Grupo> tableView;
    @FXML
    private TableColumn<Grupo, String> tcGrupo;
    private ObservableList<Grupo> grupos = FXCollections.observableArrayList();
    @FXML
    private TextField tfNome;
    @FXML
    private Button btSalvar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcGrupo.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        grupos.setAll(new GrupoDAO().listarTodos());
        tableView.setItems(grupos);
        
        btSalvar.setOnAction((ActionEvent event) -> {
            new GrupoDAO().save(new Grupo(tfNome.getText()));
            grupos.setAll(new GrupoDAO().listarTodos());
            Dialog.information("Grupo salvo com sucesso.");
            tfNome.setText(null);
        });
        
    }
    
}
