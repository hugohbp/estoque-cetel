/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.FornecedorDAO;
import br.org.senai.entities.Fornecedor;
import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CadastroDeFornecedoresController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Fornecedor fornecedor;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfEndereco;

    @FXML
    private TextField tfTelefone;

    @FXML
    private ImageView iconeNome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fornecedor = new Fornecedor();
        tfNome.setText(null);
        tfEndereco.setText(null);
        tfTelefone.setText(null);
        iconeNome.setImage(null);
    }

    @FXML
    public void cancelar() {
        ((Stage) tfNome.getScene().getWindow()).close();
    }

    @FXML
    public void salvar() {

        String errorMessage = "";

        try {

            if (tfNome.getText().isEmpty() || new FornecedorDAO().validarFornecedor(fornecedor) != null) {
                errorMessage += "Nome do fornecedor inválido! \n";
            }

            if (tfEndereco.getText().isEmpty()) {
                errorMessage += "Endereço do fornecedor inválido!\n";
            }

            if (tfTelefone.getText().isEmpty()) {
                errorMessage += "Telefone inválido!";
            }

        } catch (NullPointerException ex) {
            errorMessage = "Campos inválidos!";
        }

        if (errorMessage.isEmpty() || errorMessage.length() == 0) {
            fornecedor.setNome(tfNome.getText());
            fornecedor.setEndereco(tfEndereco.getText());
            fornecedor.setTelefone(tfTelefone.getText());

            new FornecedorDAO().save(fornecedor);

            Dialog.information("Fornecedor salvo com sucesso!");

            initialize(null, null);

        } else {
            Dialog.error(errorMessage);
        }
    }

    @FXML
    public void validarNome() {

        fornecedor.setNome(tfNome.getText());

        if (!tfNome.getText().isEmpty() && new FornecedorDAO().validarFornecedor(fornecedor) == null) {
            iconeNome.setImage(Config.ICONE_OK);
        } else {
            iconeNome.setImage(Config.ICONE_ERRADO);
        }

    }

}
