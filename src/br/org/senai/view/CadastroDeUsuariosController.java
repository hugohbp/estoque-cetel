/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.GrupoDAO;
import br.org.senai.dao.UsuarioDAO;
import br.org.senai.entities.Grupo;
import br.org.senai.entities.Usuario;
import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CadastroDeUsuariosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Usuario usuario;
    
    @FXML
    private TextField tfNome;
    
    @FXML
    private TextField tfLogin;
    
    @FXML
    private TextField tfEmail;
    
    @FXML
    private PasswordField pfSenha;
    
    @FXML
    private PasswordField pfConfirmarSenha;
    
    @FXML
    private ComboBox<Grupo> cbTipoDeUsuario;
    
    @FXML
    private TableView<Usuario> tableView;
    
    @FXML
    private TableColumn<Usuario, String> nome;
    @FXML
    private TableColumn<Usuario, String> login;
    @FXML
    private TableColumn<Usuario, String> email;
    @FXML
    private TableColumn<Grupo, String> tipoDeUsuario;
    
    private ObservableList<Usuario> data;
    
    private ValidationSupport validationSupport = new ValidationSupport();
    
    @FXML
    private Button btSalvar;
    
    @FXML
    private Button btCancelar;
    
    @FXML
    private Button btExcluir;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbTipoDeUsuario.valueProperty().set(null);
        usuario = new Usuario();
        
        Ferramenta.fillComboBox(cbTipoDeUsuario, new GrupoDAO().listarTodos());
        
        tfNome.setText(null);
        tfLogin.setText(null);
        pfConfirmarSenha.setText(null);
        pfSenha.setText(null);
        tfEmail.setText(null);
        
        data = FXCollections.observableArrayList();
        
        data.setAll(new UsuarioDAO().listarTodos());
        
        nome.setCellValueFactory(new PropertyValueFactory("nome"));
        
        login.setCellValueFactory(new PropertyValueFactory("login"));
        
        email.setCellValueFactory(new PropertyValueFactory("email"));
        
        tipoDeUsuario.setCellValueFactory(new PropertyValueFactory("grupo"));
        
        tableView.setItems(data);
        
        validationSupport.registerValidator(tfNome, true, Validator.createEmptyValidator("Digite o nome do usuário."));
        validationSupport.registerValidator(tfLogin, (Control c, String valor) -> ValidationResult.fromErrorIf((TextField) c, "Login inválido ou já existente.", !Ferramenta.validarLogin(tfLogin.getText())));
        validationSupport.registerValidator(tfEmail, (Control c, String valor) -> ValidationResult.fromErrorIf((TextField) c, "Email já existente ou inválido.", !Ferramenta.validarEmail(tfEmail.getText())));
        validationSupport.registerValidator(pfConfirmarSenha, (Control c, String valor) -> ValidationResult.fromErrorIf((PasswordField) c, "Senhas não correspondem.", pfSenha.getText() != null ? !(pfSenha.getText().equals(pfConfirmarSenha.getText())) : false));
        validationSupport.registerValidator(cbTipoDeUsuario, Validator.createEqualsValidator("Selecione um grupo.", new GrupoDAO().listarTodos()));
        
        btSalvar.setOnAction((ActionEvent event) -> {
            salvar();
        });
        
        btCancelar.setOnAction((ActionEvent event) -> {
            cancelar();
        });
        
        btExcluir.setOnAction((ActionEvent event) -> {
            excluir();
        });
        
    }
    
    public void cancelar() {
        ((Stage) tfNome.getScene().getWindow()).close();
    }
    
    public void salvar() {
        
        String errorMessage = "";
        
        for (ValidationMessage message : validationSupport.getValidationResult().getErrors()) {
            errorMessage += "-> " + message.getText() + "\n";
        }
        
        if (errorMessage.isEmpty()) {
            
            usuario.setNome(tfNome.getText());
            usuario.setLogin(tfLogin.getText());
            usuario.setEmail(tfEmail.getText());
            usuario.setSenha(pfConfirmarSenha.getText());
            usuario.setGrupo(cbTipoDeUsuario.getValue());
            
            new UsuarioDAO().save(usuario);
            
            Dialog.information("Usuário salvo com sucesso!");
            
            initialize(null, null);
            
        } else {
            Dialog.error(errorMessage);
        }
        
    }
    
    public void excluir() {
        
        usuario = null;
        
        usuario = tableView.getSelectionModel().getSelectedItem();
        
        if (usuario != null) {
            
            if (Dialog.confirmation("Deseja apagar o usuário?\nNome: " + usuario.getNome() + "\nEmail: " + usuario.getEmail())) {
                new UsuarioDAO().delete(usuario);
                Dialog.information("Usuário deletado.");
                
                data = FXCollections.observableArrayList();
                
                data.setAll(new UsuarioDAO().listarTodos());
                
                tableView.setItems(data);
            }
            
            usuario = new Usuario();
        }
        
    }
    
    public void alterar() {
        initialize(null, null);
        
        usuario = null;
        usuario = tableView.getSelectionModel().getSelectedItem();
        
        if (usuario != null) {
            tfNome.setText(usuario.getNome());
            tfLogin.setText(usuario.getLogin());
        }
        
    }
    
}
