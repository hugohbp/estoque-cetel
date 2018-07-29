/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.view;

import br.org.senai.dao.TipoDoCursoDAO;
import br.org.senai.dao.ItensSolicitacaoDAO;
import br.org.senai.dao.ProdutoDAO;
import br.org.senai.dao.SolicitacaoDAO;
import br.org.senai.dao.StatusDAO;
import br.org.senai.entities.TipoDoCurso;
import br.org.senai.entities.ItensSolicitacao;
import br.org.senai.entities.Produto;
import br.org.senai.entities.Solicitacao;
import br.org.senai.entities.Status;
import br.org.senai.util.Config;
import br.org.senai.util.Dialog;
import br.org.senai.util.Ferramenta;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author Simulado
 */
public class CadastroDeSolicitacoesController1 implements Initializable {

    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableView<Produto> tableView;

    @FXML
    private TableColumn<Produto, Image> tcImagemProduto;

    @FXML
    private TableColumn<Produto, String> tcNomeProduto;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private ComboBox<TipoDoCurso> cbTipoDoCurso;

//    @FXML
//    private Label lbItensNoCarrinho;
//    @FXML
//    private ListView<ItensSolicitacao> itens;
    @FXML
    private Button btAddProduto;

//    @FXML
//    private Button btRemoverProduto;
    @FXML
    private Accordion accordion;

    @FXML
    private Button irParaFinalizarSolicitacao;

    @FXML
    private Button btFinalizarSolicitacao;

    @FXML
    private TableView<ItensSolicitacao> tableViewItens;

    @FXML
    private TableColumn<ItensSolicitacao, Image> tcImagemItemSolicitado;

    @FXML
    private TableColumn<ItensSolicitacao, String> tcNomeItemSolicitado;

    @FXML
    private TableColumn<ItensSolicitacao, Integer> tcQuantidadeItemSolicitado;

    @FXML
    private TableColumn<ItensSolicitacao, String> tcTipoDoCurso;

    @FXML
    private DatePicker dpDataNecessidade;

    @FXML
    private Pane pane;

    @FXML
    private Label lbQuantidadeDeItens;

    @FXML
    private AnchorPane detalhesSolicitacao;

    @FXML
    private ImageView ivIconeCesta;

    @FXML
    private ScrollPane scrollPane;

    ObservableList<Produto> produtos = FXCollections.observableArrayList();
    ObservableList<ItensSolicitacao> itensSolicitados = FXCollections.observableArrayList();

    private Produto produtoSelecionado;

    HashSet<ItensSolicitacao> hashItensSolicitacao = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ivIconeCesta.setImage(new Image("file:imagens/icones/carrinho.png"));
        produtos.clear();
        itensSolicitados.clear();
        hashItensSolicitacao.clear();
        dpDataNecessidade.setValue(null);

        detalhesSolicitacao.setVisible(false);

        accordion.setExpandedPane(accordion.getPanes().get(0));

        produtos.setAll(new ProdutoDAO().listarTodos());

        tcImagemProduto.setCellValueFactory((TableColumn.CellDataFeatures<Produto, Image> param) -> {
            if (param.getValue().getImagem() == null) {
                return new SimpleObjectProperty<>(new Image("file:imagens/icones/semFoto.jpg"));
            } else {
                return new SimpleObjectProperty<>(new Image(new ByteArrayInputStream(param.getValue().getImagem())));
            }
        });

        tcImagemProduto.setCellFactory((TableColumn<Produto, Image> param) -> new TableCell<Produto, Image>() {

            @Override
            protected void updateItem(Image item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    ImageView imageView = new ImageView(item);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(75d);
                    setGraphic(imageView);

                }
                setAlignment(Pos.CENTER);
            }

        });

        tcNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));

        tableView.setItems(produtos);

        ///////////////////////////TABELA ITENS SOLICITADOS///////////////////////////////////
        tcImagemItemSolicitado.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, Image> param) -> {
            if (param.getValue().getItensSolicitacaoPK().getProduto().getImagem() == null) {
                return new SimpleObjectProperty<>(new Image("file:imagens/icones/semFoto.jpg"));
            } else {
                return new SimpleObjectProperty<>(new Image(new ByteArrayInputStream(param.getValue().getItensSolicitacaoPK().getProduto().getImagem())));
            }
        });

        tcImagemItemSolicitado.setCellFactory((TableColumn<ItensSolicitacao, Image> param) -> new TableCell<ItensSolicitacao, Image>() {

            @Override
            protected void updateItem(Image item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    ImageView imageView = new ImageView(item);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(75d);
                    setGraphic(imageView);

                }
                setAlignment(Pos.CENTER);
            }

        });

        tcNomeItemSolicitado.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, String> param) -> new SimpleStringProperty(param.getValue().getItensSolicitacaoPK().getProduto().getNome()));

        tcQuantidadeItemSolicitado.setCellValueFactory(new PropertyValueFactory<>("quantidadeSolicitada"));

        tcTipoDoCurso.setCellValueFactory((TableColumn.CellDataFeatures<ItensSolicitacao, String> param) -> new SimpleStringProperty(param.getValue().getTipoDoCurso().getDescricao()));

        //////////////////////////////////////////////////////////////
//        if (new TipoDoCursoDAO().listarTodos().isEmpty()) {
//            
//            new TipoDoCursoDAO().save(new TipoDoCurso("Aprendizagem", 25));
//            new TipoDoCursoDAO().save(new TipoDoCurso("Técnico", 30));
//            new TipoDoCursoDAO().save(new TipoDoCurso("Especilização", 470));
//        }
        Ferramenta.fillComboBox(cbTipoDoCurso, new TipoDoCursoDAO().listarTodos());

        tableView.setOnMouseClicked(
                (MouseEvent event) -> {
                    produtoSelecionado = tableView.getSelectionModel().getSelectedItem();
                }
        );

        btAddProduto.setOnAction(
                (ActionEvent event) -> {
                    if (produtoSelecionado != null) {
                        ItensSolicitacao itensSolicitacao = new ItensSolicitacao();
                        itensSolicitacao.getItensSolicitacaoPK().setProduto(produtoSelecionado);

                        if (!tfQuantidade.getText().isEmpty() && cbTipoDoCurso.getValue() != null) {

                            if (hashItensSolicitacao.contains(itensSolicitacao)) {

                                if (Dialog.confirmation("Deseja atualizar o valor do produto: " + itensSolicitacao.getItensSolicitacaoPK().getProduto().getNome())) {
                                    hashItensSolicitacao.remove(itensSolicitacao);
                                }

                            }

                            itensSolicitacao.setTipoDoCurso(cbTipoDoCurso.getValue());
                            itensSolicitacao.setQuantidadeSolicitada(Integer.parseInt(tfQuantidade.getText()));

                            if (new StatusDAO().listarTodos().isEmpty()) {
                                new StatusDAO().save(new Status("Aguardando supervisão."));
                            }

                            itensSolicitacao.getItensSolicitacaoPK().setProduto(produtoSelecionado);

                            itensSolicitacao.setStatus(new StatusDAO().getStatus("Aguardando supervisão."));

                            hashItensSolicitacao.add(itensSolicitacao);

                            encherCarrinho();

                            Dialog.information("Item adicionado.");

                        } else {
                            Dialog.information("Preencha os campos.");
                        }

                    } else {
                        Dialog.information("Selecione um produto!");
                    }
                }
        );

        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setOnMouseMoved((MouseEvent event) -> {
            detalhesSolicitacao.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            detalhesSolicitacao.setVisible(true);
        });

        detalhesSolicitacao.setOnMouseExited((MouseEvent event) -> {
            detalhesSolicitacao.setVisible(false);
        });

//        btRemoverProduto.setOnAction(
//                (ActionEvent event) -> {
//                    ItensSolicitacao itensSolicitacao = itens.getSelectionModel().getSelectedItem();
//                    if (itensSolicitacao != null) {
//                        if (Dialog.confirmation("Deseja remover o produto: " + itensSolicitacao.getItensSolicitacaoPK().getProduto().getNome() + ";\nQuantidade: " + itensSolicitacao.getQuantidadeSolicitada() + ";\nCentro de custo: " + itensSolicitacao.getTipoDoCurso().getDescricao())) {
//                            
//                            hashItensSolicitacao.remove(itensSolicitacao);
//                            
//                            encherCarrinho();
//                            Dialog.information("Item removido.");
//                        }
//                    } else {
//                        Dialog.information("Selecione um produto.");
//                    }
//                }
//        );
//        
        tfPesquisar.setOnKeyReleased((KeyEvent event) -> {
            Platform.runLater(() -> {
                produtos.setAll(new ProdutoDAO().getProdutoPorNome(tfPesquisar.getText()));
            });
        });
        irParaFinalizarSolicitacao.setOnAction(
                (ActionEvent event) -> {
                    accordion.setExpandedPane(accordion.getPanes().get(1));

                }
        );

        btFinalizarSolicitacao.setOnAction((ActionEvent event) -> {
            if (dpDataNecessidade.getValue() != null && !hashItensSolicitacao.isEmpty()) {

                Solicitacao solicitacao = new Solicitacao();

                solicitacao.setDataSolicitacao(new java.util.Date());
                solicitacao.setStatus(StatusDAO.emAberto);
                solicitacao.setUsuario(Config.usuarioLogado);

                solicitacao.setDataNecessidade(Date.from(dpDataNecessidade.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                new SolicitacaoDAO().save(solicitacao);

                for (ItensSolicitacao itensSolicitacao : hashItensSolicitacao) {

                    itensSolicitacao.getItensSolicitacaoPK().setSolicitacao(solicitacao);

                    new ItensSolicitacaoDAO().save(itensSolicitacao);
                }

                Dialog.information("Solicitação salva com sucesso.");

                initialize(url, rb);

            } else {
                Dialog.warning("Preecha os campos corretamente.");
            }
        });

    }

    private void encherCarrinho() {

        if (!hashItensSolicitacao.isEmpty()) {

            ObservableList<ItensSolicitacao> solicitacao = FXCollections.observableArrayList();

            solicitacao.setAll(hashItensSolicitacao);

            int quantidade = hashItensSolicitacao.size();

            if (quantidade == 1) {
                lbQuantidadeDeItens.setText(quantidade + " item");
            } else {
                lbQuantidadeDeItens.setText(quantidade + " itens");
            }

            String produtos = "";

            GridPane gridPane = new GridPane();

            gridPane.setHgap(50);
            gridPane.setPadding(new Insets(10d));
            gridPane.setVgap(20);
            gridPane.setVisible(true);

            int linha = 0;

            for (ItensSolicitacao itensSolicitacao : hashItensSolicitacao) {
                produtos += itensSolicitacao.getItensSolicitacaoPK().getProduto().getNome() + "; " + itensSolicitacao.getQuantidadeSolicitada() + " itens.\n";
                VBox vBox = new VBox(5);
                vBox.setAlignment(Pos.CENTER);
                vBox.managedProperty().bind(vBox.visibleProperty());
                ImageView imageView = new ImageView();
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(75d);
                if (itensSolicitacao.getItensSolicitacaoPK().getProduto().getImagem() == null) {
                    imageView.setImage(Config.ICONE_SEM_IMAGEM);
                } else {
                    imageView.setImage(new Image(new ByteArrayInputStream(itensSolicitacao.getItensSolicitacaoPK().getProduto().getImagem())));
                }
                vBox.getChildren().add(imageView);
                Label label = new Label(itensSolicitacao.getItensSolicitacaoPK().getProduto().getNome());
                label.setFont(Font.font(null, FontWeight.BOLD, 12));
                vBox.getChildren().add(label);

                Label label1 = new Label(Integer.toString(itensSolicitacao.getQuantidadeSolicitada()));

                label1.setAlignment(Pos.CENTER);

                Button button = new Button("Remover");

                button.setOnAction((ActionEvent event) -> {
                    if (Dialog.confirmation("Deseja remover o produto: " + itensSolicitacao.getItensSolicitacaoPK().getProduto().getNome() + ";\nQuantidade: " + itensSolicitacao.getQuantidadeSolicitada() + ";\nCentro de custo: " + itensSolicitacao.getTipoDoCurso().getDescricao())) {
                        hashItensSolicitacao.remove(itensSolicitacao);
                        encherCarrinho();
                        Dialog.information("Item removido.");
                    }
                });
                gridPane.addRow(linha, vBox, label1, button);
                linha++;
            }

            GridPane.setHalignment(gridPane, HPos.CENTER);

            gridPane.setAlignment(Pos.CENTER);
            scrollPane.setContent(gridPane);

            lbQuantidadeDeItens.setTooltip(new Tooltip(produtos));

            itensSolicitados.setAll(hashItensSolicitacao);

            tableViewItens.setItems(itensSolicitados);
        }
    }
}
