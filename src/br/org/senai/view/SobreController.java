/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.util.Config;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class SobreController implements Initializable {

    @FXML
    private Label lbNomeSistema;

    @FXML
    private TextArea taDescricao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lbNomeSistema.setText(Config.NOME_SISTEMA + "\nVersão: " + Config.VERSAO_SISTEMA);
        
        taDescricao.setText("Sistema em desenvolvimento.\nProgramador: Hugo Henrique");

    }

}
