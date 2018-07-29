/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.util.Config;
import br.org.senai.util.Ferramenta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class NotificacoesController implements Initializable {

    @FXML
    private ComboBox<Integer> cbIntervalo;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Button btSalvar;
    private ObservableList<Integer> intervalos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Ferramenta.lerOpcoes();
        checkBox.setSelected(Config.notificacaoAtiva);
        cbIntervalo.valueProperty().set(null);
        intervalos.add(1);
        intervalos.add(3);
        intervalos.add(5);
        intervalos.add(10);
        intervalos.add(15);
        intervalos.add(30);
        intervalos.add(60);
        cbIntervalo.setItems(intervalos);
        cbIntervalo.setValue(Config.notificacaoIntervalo);
        btSalvar.setOnAction((ActionEvent event) -> {
            if (cbIntervalo.getSelectionModel().getSelectedItem() != null) {
                Ferramenta.salvarOpcoes(checkBox.isSelected() ? "true" : "false", Integer.toString(cbIntervalo.getSelectionModel().getSelectedItem()));
            } else {
                Ferramenta.salvarOpcoes(checkBox.isSelected() ? "true" : "false", Integer.toString(30));

            }
        });

    }

}
