/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.UsuarioDAO;
import br.org.senai.entities.Usuario;
import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import br.org.senai.util.Notificacao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class LoginController implements Initializable {

    @FXML
    private Button btLogar;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private TextField tfUsuario;

    @FXML
    private Label lbEsqueciASenha;

    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pfSenha.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                logar();
            }

        });

        btLogar.setOnAction((ActionEvent event) -> {
            logar();
        });

        tfUsuario.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                logar();
            }
        });

        lbEsqueciASenha.setOnMouseClicked((MouseEvent event) -> {
            Dialog.abrirJanela("RecuperarSenha", "Recuperar senha", true, false, false);
        });
    }

    public void logar() {
        if (!tfUsuario.getText().isEmpty() && !pfSenha.getText().isEmpty()) {

            usuario = new Usuario();

            usuario.setLogin(tfUsuario.getText());
            usuario.setSenha(pfSenha.getText());

            if (new UsuarioDAO().logarValidarLogin(usuario) != null) {
                if (new UsuarioDAO().logarValidarLoginESenha(usuario) != null) {
                    Config.usuarioLogado = new UsuarioDAO().logarValidarLoginESenha(usuario);
                    Stage stage = (Stage) btLogar.getScene().getWindow();
                    stage.close();
                   // Stage menuPrincipal = new Stage();
                    Dialog.abrirJanela("MenuPrincipal", "Menu Principal", false, true, true);

                    Notificacao notificacao = new Notificacao(Config.notificacaoAtiva, Config.notificacaoIntervalo);

                    notificacao.executar();

                } else {
                    pfSenha.requestFocus();
                    Dialog.information("Senha inválida.");
                }
            } else {
                tfUsuario.requestFocus();
                Dialog.information("Usuário não encontrado.");
            }
        } else {
            Dialog.error("Preecha os campos.");
        }

    }

}
