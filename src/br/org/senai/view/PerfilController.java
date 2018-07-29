/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.UsuarioDAO;
import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import br.org.senai.util.Encripta;
import br.org.senai.util.Ferramenta;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
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
public class PerfilController implements Initializable {

    @FXML
    private Label lbLogin;

    @FXML
    private Label lbGrupo;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfSenhaAtual;

    @FXML
    private PasswordField pfNovaSenha;

    @FXML
    private PasswordField pfRepitaASenha;

    @FXML
    private ImageView ivFotoDoPerfil;

    @FXML
    private CheckBox checkBoxAlterarSenha;

    @FXML
    private Button btInserirFoto;

    @FXML
    private Button btRemoverFoto;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    private File file;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pfSenhaAtual.setDisable(!checkBoxAlterarSenha.isSelected());
        pfNovaSenha.setDisable(!checkBoxAlterarSenha.isSelected());
        pfRepitaASenha.setDisable(!checkBoxAlterarSenha.isSelected());

        lbLogin.setText(Config.usuarioLogado.getLogin());
        lbGrupo.setText(Config.usuarioLogado.getGrupo().getDescricao());

        if (Config.usuarioLogado.getFoto() == null) {
            ivFotoDoPerfil.setImage(Config.ICONE_SEM_IMAGEM);
        } else {
            ivFotoDoPerfil.setImage(Ferramenta.converterByteParaImage(Config.usuarioLogado.getFoto()));
            
        }

        tfNome.setText(Config.usuarioLogado.getNome());
        tfEmail.setText(Config.usuarioLogado.getEmail());

        ValidationSupport validationSupport = new ValidationSupport();

        validationSupport.registerValidator(tfNome, Validator.createEmptyValidator("Digite o seu nome."));
        validationSupport.registerValidator(tfEmail, (Control c, String valor) -> ValidationResult.fromErrorIf((TextField) c, "Email já existente ou inválido.", !Ferramenta.validarEmail(tfEmail.getText())));

        //EVENTOS 
        btInserirFoto.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir imagem");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Todas as imagens", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"));
            file = fileChooser.showOpenDialog(btInserirFoto.getScene().getWindow());

            if (file != null) {
                Platform.runLater(() -> {
                    Config.usuarioLogado.setFoto(Ferramenta.converterArquivoParaByte(file));
                    ivFotoDoPerfil.setImage(Ferramenta.converterByteParaImage(Config.usuarioLogado.getFoto()));
                });
            }

        });

        btRemoverFoto.setOnAction((ActionEvent event) -> {

            if (file != null) {
                if (Dialog.confirmation("Deseja remover foto do perfil?")) {
                    file = null;
                    Config.usuarioLogado.setFoto(null);
                    ivFotoDoPerfil.setImage(Config.ICONE_SEM_IMAGEM);
                }
            }
        });
        checkBoxAlterarSenha.setOnAction((ActionEvent event) -> {

            pfSenhaAtual.setDisable(!checkBoxAlterarSenha.isSelected());
            pfNovaSenha.setDisable(!checkBoxAlterarSenha.isSelected());
            pfRepitaASenha.setDisable(!checkBoxAlterarSenha.isSelected());

            if (checkBoxAlterarSenha.isSelected()) {
                validationSupport.registerValidator(pfSenhaAtual, (Control c, String valor) -> ValidationResult.fromErrorIf((PasswordField) c, "Senha atual errada.", !(Config.usuarioLogado.getSenha().equals(Encripta.criptografarSenha(pfSenhaAtual.getText())) && checkBoxAlterarSenha.isSelected())));
                validationSupport.registerValidator(pfRepitaASenha, (Control c, String valor) -> ValidationResult.fromErrorIf((PasswordField) c, "Senhas não correspondem.", !(pfNovaSenha.getText().equals(pfRepitaASenha.getText()) && checkBoxAlterarSenha.isSelected())));
                validationSupport.redecorate();
            }

        });

        btSalvar.setOnAction((ActionEvent event) -> {

            String texto = "";

            for (ValidationMessage message : validationSupport.getValidationResult().getErrors()) {
                texto += message.getText();
            }

            if (texto.isEmpty()) {
                Config.usuarioLogado.setNome(tfNome.getText());
                Config.usuarioLogado.setEmail(tfEmail.getText());

                if (checkBoxAlterarSenha.isSelected()) {
                    Config.usuarioLogado.setSenha(Encripta.criptografarSenha(pfRepitaASenha.getText()));
                }

                new UsuarioDAO().update(Config.usuarioLogado);

                Dialog.information("Perfil atualizado.");
                ((Stage) btSalvar.getScene().getWindow()).close();

                if (checkBoxAlterarSenha.isSelected()) {
                    Dialog.abrirJanela("Login", "Login", true, false, false);
                }

            } else {
                Dialog.error(texto);
            }

        });

        btCancelar.setOnAction((ActionEvent event) -> {
            ((Stage) btCancelar.getScene().getWindow()).close();
        });

    }



}
