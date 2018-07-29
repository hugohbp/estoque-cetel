/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.LocalDeArmazenamentoDAO;
import br.org.senai.entities.LocalDeArmazenamento;
import br.org.senai.util.Dialog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CadastroDeLocaisDeArmazenamentoController implements Initializable {

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    @FXML
    private TextField field;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btSalvar.setOnAction((ActionEvent event) -> {
            new LocalDeArmazenamentoDAO().save(new LocalDeArmazenamento(field.getText()));
            Dialog.information("Local de armazenamento cadastrado com sucesso!");
            ((Stage) btCancelar.getScene().getWindow()).close();
        });

        btCancelar.setOnAction((ActionEvent event) -> {

            if (Dialog.confirmation("Deseja cancelar cadastro?")) {
                ((Stage) btCancelar.getScene().getWindow()).close();
            }

        });

    }

}
