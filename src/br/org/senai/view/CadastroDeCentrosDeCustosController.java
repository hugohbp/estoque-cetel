/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.TipoDoCursoDAO;
import br.org.senai.entities.TipoDoCurso;
import br.org.senai.util.Dialog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CadastroDeCentrosDeCustosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tfNomeDoCurso;

    @FXML
    private TextField tfCodigo;

    @FXML
    private Button btRemover;

    @FXML
    private TableView<TipoDoCurso> tableView;

    @FXML
    private TableColumn<TipoDoCurso, String> tcCentroDeCusto;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    private TipoDoCurso tipoDoCurso;

    private ObservableList<TipoDoCurso> listCentroDeCusto = FXCollections.observableArrayList();

    private boolean editando = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            listCentroDeCusto.clear();

            editando = false;

            tipoDoCurso = null;

            tfCodigo.setText(null);
            tfNomeDoCurso.setText(null);

            btSalvar.setText("Salvar");

            listCentroDeCusto.setAll(new TipoDoCursoDAO().listarTodos());

            tcCentroDeCusto.setCellValueFactory((TableColumn.CellDataFeatures<TipoDoCurso, String> param) -> {
                if (param.getValue().getCentroDeCusto() == 0) {
                    return new SimpleObjectProperty<>(param.getValue().getDescricao());
                } else {
                    return new SimpleObjectProperty<>(param.getValue().getDescricao() + " - " + param.getValue().getCentroDeCusto());
                }
            });

            tableView.setItems(listCentroDeCusto);
//            ValidationSupport validationSupport = new ValidationSupport();

//            validationSupport.registerValidator(tfNomeDoCurso, Validator.createEmptyValidator("Digite o nome do curso!"));
//            validationSupport.registerValidator(tfCodigo, Validator.createEmptyValidator("Digite o código correspondente ao curso!"));
            btSalvar.setOnAction((ActionEvent event) -> {

                String texto = "";

                if (tfNomeDoCurso.getText() == null || tfNomeDoCurso.getText().isEmpty()) {
                    texto += "Digite o nome do curso!\n";
                }
                if (tfCodigo.getText() == null || tfNomeDoCurso.getText().isEmpty()) {
                    texto += "Digite o código correspondente ao curso!\n";
                }

                if (texto.isEmpty()) {

                    if (editando) {
                        tipoDoCurso.setCentroDeCusto(Integer.parseInt(tfCodigo.getText()));
                        tipoDoCurso.setDescricao(tfNomeDoCurso.getText());
                        new TipoDoCursoDAO().update(tipoDoCurso);
                        Dialog.information("Centro de custo atualizado!");
                    } else {
                        new TipoDoCursoDAO().save(new TipoDoCurso(tfNomeDoCurso.getText(), Integer.parseInt(tfCodigo.getText())));
                        Dialog.information("Centro de custo cadastrado com sucesso!");
                    }
                    initialize(url, rb);
                } else {
                    Dialog.error(texto);
                }

            });

            btRemover.setOnAction((ActionEvent event) -> {

                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    TipoDoCurso centroDeCusto = tableView.getSelectionModel().getSelectedItem();
                    if (Dialog.confirmation("Deseja remover o centro de custo: " + centroDeCusto.getDescricao() + "\n Código: " + centroDeCusto.getDescricao())) {
                        new TipoDoCursoDAO().delete(centroDeCusto);
                        Dialog.information("Centro de custo removido.");
                        initialize(url, rb);
                    }
                }
            });

            tableView.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 2) {
                    if (tableView.getSelectionModel().getSelectedItem() != null) {
                        editando = true;
                        tipoDoCurso = tableView.getSelectionModel().getSelectedItem();
                        tfNomeDoCurso.setText(tipoDoCurso.getDescricao());
                        tfCodigo.setText(Integer.toString(tipoDoCurso.getCentroDeCusto()));
                        btSalvar.setText("Salvar alterações");
                    }
                }
            });

            btCancelar.setOnAction((ActionEvent event) -> {
                editando = false;
                btSalvar.setText("Salvar");
                tipoDoCurso = null;
                tfCodigo.setText(null);
                tfNomeDoCurso.setText(null);
            });
        });
    }
}
