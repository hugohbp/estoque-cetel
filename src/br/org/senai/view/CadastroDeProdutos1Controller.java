/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.LocalDeArmazenamentoDAO;
import br.org.senai.dao.MarcacaoDAO;
import br.org.senai.dao.ProdutoDAO;
import br.org.senai.dao.TagDAO;
import br.org.senai.entities.LocalDeArmazenamento;
import br.org.senai.entities.Marcacao;
import br.org.senai.entities.Produto;
import br.org.senai.entities.Tag;
import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.InfoOverlay;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CadastroDeProdutos1Controller implements Initializable {

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btAddImagem;

    @FXML
    private Button btRemoverImagem;

    @FXML
    private ImageView ivProduto;

    @FXML
    private Button btCadastrarLocalDeArmazenamento;

    @FXML
    private TextField tfNome;

    @FXML
    private TextArea taDescricao;

    ObservableList<Produto> observableArrayList = FXCollections.observableArrayList();

    private File file;

    private Produto produto;

    @FXML
    private ComboBox<LocalDeArmazenamento> cbLocaisDeArmazenamento;

    private ObservableList<LocalDeArmazenamento> locaisDeArmazenamento = FXCollections.observableArrayList();

    @FXML
    private Button btAddMarcador;

    @FXML
    private ComboBox<Tag> cbMarcadores;

    @FXML
    private TextArea taMarcadoresAdicionados;

    private HashSet<Tag> listaTags = new HashSet<>();

    @FXML
    private Button btRemoverMarcadores;

    @FXML
    private TabPane pane;

    @FXML
    private Label lbTitulo;

    private String erros = "";

    private ObjectProperty<javafx.scene.image.Image> imageProperty = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(() -> {

            listaTags.clear();
            encherTaMarcadoresAdicionados();

            Ferramenta.fillComboBox(cbLocaisDeArmazenamento, new LocalDeArmazenamentoDAO().listarTodos());
            Ferramenta.fillComboBox(cbMarcadores, new TagDAO().listarTodos());

            if (lbTitulo.getParent().getUserData() == null) {
                produto = new Produto();

                tfNome.setText(null);
                taDescricao.setText(null);

                file = null;
                ivProduto.setImage(new Image("file:imagens/icones/semFoto.jpg"));

                System.out.println("fjlsdfjsdlfjsdl");

            } else {
                lbTitulo.setText("Editar produto");
                produto = (Produto) lbTitulo.getParent().getUserData();
                tfNome.setText(produto.getNome());
                taDescricao.setText(produto.getDescricao());
                cbLocaisDeArmazenamento.setValue(produto.getLocalDeArmazenamento());
                listaTags.addAll(new MarcacaoDAO().getTagsDoProduto(produto));

                if (produto.getImagem() != null) {
                    ivProduto.setImage(new Image(new ByteArrayInputStream(produto.getImagem())));
                    file = new File("/br");
                } else {
                    ivProduto.setImage(new Image("file:imagens/icones/semFoto.jpg"));
                }

            }
        });

        InfoOverlay infoOverlay = new InfoOverlay(ivProduto, "fsldkfjlsdfjsdlf");

        infoOverlay.setShowOnHover(true);
        infoOverlay.setTooltip(new Tooltip("fsdfdsfdsfdsfds"));
        infoOverlay.setVisible(true);
        infoOverlay.autosize();
        infoOverlay.setContent(ivProduto);

        Bindings.bindBidirectional(this.ivProduto.imageProperty(), imageProperty);

        ivProduto.setOnMouseEntered((MouseEvent event) -> {

        });

        ValidationSupport support = new ValidationSupport();

        support.registerValidator(tfNome, true, Validator.createEmptyValidator("Digite o nome do usuário."));
        support.registerValidator(taDescricao, true, Validator.createEmptyValidator("Digite a descrição do produto."));
        support.registerValidator(cbLocaisDeArmazenamento, Validator.createEqualsValidator("Selecione um local de armazenamento.", new LocalDeArmazenamentoDAO().listarTodos()));

        locaisDeArmazenamento.setAll(new LocalDeArmazenamentoDAO().listarTodos());

        btAddImagem.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir imagem");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Todas as imagens", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"));
            file = fileChooser.showOpenDialog(btAddImagem.getScene().getWindow());

            if (file != null) {

                Platform.runLater(() -> {
                    produto.setImagem(Ferramenta.converterArquivoParaByte(file));
                    ivProduto.setImage(new Image(new ByteArrayInputStream(produto.getImagem())));

                });

            }

        });

        btRemoverImagem.setOnAction((ActionEvent event) -> {

            if (file != null) {
                if (Dialog.confirmation("Deseja remover imagem?")) {
                    file = null;
                    produto.setImagem(null);
                    ivProduto.setImage(new Image("file:imagens/icones/semFoto.jpg"));
                }
            }

        });

        btCadastrarLocalDeArmazenamento.setOnAction((ActionEvent event) -> {

            Platform.runLater(() -> {
                Dialog.abrirJanela("CadastroDeLocaisDeArmazenamento", "Cadastrar locais de armazenamento");
                Ferramenta.fillComboBox(cbLocaisDeArmazenamento, new LocalDeArmazenamentoDAO().listarTodos());
            });
        });

        cbLocaisDeArmazenamento.setOnAction((ActionEvent event) -> {

            if (cbLocaisDeArmazenamento.getSelectionModel().getSelectedItem() instanceof LocalDeArmazenamento) {
                produto.setLocalDeArmazenamento(cbLocaisDeArmazenamento.getSelectionModel().getSelectedItem());
            }

        });

        btSalvar.setOnAction((ActionEvent event) -> {

            erros = "";

            for (ValidationMessage mensagem : support.getValidationResult().getErrors()) {
                erros += mensagem.getText() + "\n";
            }

            Platform.runLater(() -> {

                if (erros.isEmpty()) {

                    produto.setNome(tfNome.getText());
                    produto.setDescricao(taDescricao.getText());

                    if (btSalvar.getParent().getUserData() == null) {

                        new ProdutoDAO().save(produto);

                        for (Tag tag : listaTags) {
                            new MarcacaoDAO().save(new Marcacao(tag, produto));
                        }

                        Dialog.information("Produto salvo com sucesso!");
                        initialize(url, rb);

                    } else {

                        new MarcacaoDAO().limparTags(produto);

                        new ProdutoDAO().update(produto);

                        for (Tag tag : listaTags) {
                            new MarcacaoDAO().save(new Marcacao(tag, produto));
                        }

                        Dialog.information("Produto atualizado com sucesso!");

                        ((Stage) btSalvar.getParent().getScene().getWindow()).close();

                    }
                } else {
                    Dialog.error(erros);
                }
            });

        });

        taMarcadoresAdicionados.setEditable(false);

        btAddMarcador.setOnAction((ActionEvent event) -> {
            String texto = Dialog.inputDialog("Digite o nome do marcador: ");
            if (!texto.isEmpty()) {
                if (Dialog.confirmation("Deseja salvar o marcador: " + texto + "?")) {
                    new TagDAO().save(new Tag(texto));
                    Ferramenta.fillComboBox(cbMarcadores, new TagDAO().listarTodos());
                    encherTaMarcadoresAdicionados();
                }
            }

        });

        btRemoverMarcadores.setOnAction((ActionEvent event) -> {
            if (!listaTags.isEmpty()) {
                if (Dialog.confirmation("Deseja remover todos os marcadores?")) {
                    listaTags.clear();
                    encherTaMarcadoresAdicionados();
                }
            }
        });

        cbMarcadores.setOnAction((ActionEvent event) -> {
            listaTags.add(cbMarcadores.getValue());
            encherTaMarcadoresAdicionados();
        });

        btCancelar.setOnAction((ActionEvent event) -> {
            if (Dialog.confirmation("Deseja cancelar cadastro do produto?")) {
                if (btCancelar.getParent().getUserData() == null) {
                    initialize(url, rb);
                } else {
                    ((Stage) btCancelar.getScene().getWindow()).close();
                }
            }
        });
    }

    public void encherTaMarcadoresAdicionados() {

        taMarcadoresAdicionados.setText("");

        String texto = "";
        for (Tag tag : listaTags) {
            try {
                texto += tag.getDescricao() + "; ";
            } catch (NullPointerException ex) {
                texto = "";
            }
        }

        taMarcadoresAdicionados.setText(texto);

    }
}
