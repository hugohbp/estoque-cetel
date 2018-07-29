/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.TokenCodeDAO;
import br.org.senai.dao.UsuarioDAO;
import br.org.senai.entities.TokenCode;
import br.org.senai.entities.Usuario;
import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import br.org.senai.util.Email;
import br.org.senai.util.Ferramenta;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.exception.ConstraintViolationException;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RecuperarSenhaController implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfCodigoSeguranca;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private PasswordField pfConfirmaSenha;

    @FXML
    private Button btEnviarCodigoDeSeguranca;

    @FXML
    private Button btTrocarSenha;
    @FXML
    private Button btCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btEnviarCodigoDeSeguranca.setOnAction((ActionEvent event) -> {
            btEnviarCodigoDeSeguranca();
        });

        btTrocarSenha.setOnAction((ActionEvent event) -> {
            btTrocarSenha();
        });

        btCancelar.setOnAction((ActionEvent event) -> {
            btCancelar();
        });

    }

    private void btEnviarCodigoDeSeguranca() {
        Usuario usuario = new Usuario();
        usuario.setEmail(tfEmail.getText());
        tfEmail.setDisable(true);
        usuario = new UsuarioDAO().pegarEmail(usuario);
        if (usuario == null) {
            Dialog.error("Não existe nenhum usuário com este email.", "E-mail inválido");
        } else {
            try {
                Email mail = new Email();
                mail.setDestinatario(tfEmail.getText());
                mail.setAssunto("Recuperar senha");
                SecureRandom random = new SecureRandom();
                String senha = Ferramenta.numeroAleatorio();
                mail.setMensagem(Config.NOME_SISTEMA + "<\br>Código de segurança:" + senha);
                TokenCode tokenCode = new TokenCode();
                tokenCode.setUsuario(usuario);
                tokenCode.setToken(senha);
                new TokenCodeDAO().save(tokenCode); 
                mail.enviarEmail();
                Dialog.information("Envio do código de segurança", "Código de segurança foi enviado!");
            } catch (UnsupportedEncodingException ex) {
                Dialog.error("Não foi possível enviar o e-mail verifique a conexão da sua internet.", "Falha de conexão");
            } catch (ConstraintViolationException e) {
                Dialog.error("Já foi enviado o e-mail espere o tempo de expiração para enviar outro e-mail!", "Falha ao reenviar e-mail");
            } catch (javax.mail.MessagingException ex) {
                Logger.getLogger(RecuperarSenhaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        tfEmail.setDisable(false);
    }

    private void btTrocarSenha() {
        Usuario usuario = new Usuario();
        usuario.setLogin(tfLogin.getText());
        usuario = new UsuarioDAO().logarValidarLogin(usuario);
        TokenCode tokenCode = new TokenCode();
        tokenCode.setToken(tfCodigoSeguranca.getText());
        tokenCode.setUsuario(usuario);
        tokenCode = new TokenCodeDAO().pegarToken(tokenCode);
        if (usuario == null || tokenCode == null) {
            Dialog.error("Login ou código de segurança estão incorretos!", "Dados não encontrado");
        } else {
            if (pfSenha.getText().equals(pfConfirmaSenha.getText())) {
                usuario.setSenha(pfSenha.getText());
                //&& pfSenha.getText().matches(".*(\\W{1,}).*")
                if (pfSenha.getText().matches(".*([a-z]{1,}).*") && pfSenha.getText().matches(".*([A-Z]{1,}).*") && pfSenha.getText().matches(".*([0-9]{1,}).*") ) {
                    new TokenCodeDAO().delete(tokenCode);
                    new UsuarioDAO().update(usuario);
                    Dialog.information("Recuperar conta", "A conta foi recuperada com sucesso!");
                    btCancelar();
                } else {
                    Dialog.error("A senha deve conter uma letra minúscula,maiúscula,número e um carácter especial.", "Senha está no formato incorreto!");
                }
            } else {
                Dialog.error("As senhas devem ser indênticas!", "As senhas não coincidem");
                pfSenha.setText("");
                pfConfirmaSenha.setText("");
                pfSenha.requestFocus();
            }
        }
    }

    private void btCancelar() {
        ((Stage) tfEmail.getScene().getWindow()).close();
    }
}
