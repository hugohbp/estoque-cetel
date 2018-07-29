/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.util;

import br.org.hugo.jdbc.dao.SolicitacaoJDAO;
import br.org.senai.dao.ItensSolicitacaoDAO;
import br.org.senai.entities.ItensSolicitacao;
import br.org.senai.entities.Solicitacao;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

/**
 *
 * @author Simulado
 */
public class Notificacao {

    private boolean ativo;
    private int intervalo;

    public Notificacao(boolean ativo, int intervalo) {
        this.ativo = ativo;
        this.intervalo = intervalo;
    }

    public Notificacao() {
        this.ativo = Config.notificacaoAtiva;
        this.intervalo = Config.notificacaoIntervalo;
    }

    public void executar() {

        Timer timer = new Timer();
        if (ativo) {
            timer.scheduleAtFixedRate(new Evento(), 0, (long) 10000);
        } else {
            timer.cancel();
        }
    }
    List<Solicitacao> solicitacaos = new SolicitacaoJDAO().pegarSolicitacoesInstrutorNotificacao(Config.usuarioLogado);
    String texto = "";

    public void notificacaoInstrutor() {

        if (!solicitacaos.isEmpty()) {
            if (solicitacaos.size() > 1) {
                texto = "";
                for (Solicitacao solicitacao : solicitacaos) {
                    for (ItensSolicitacao itensSolicitacao : new ItensSolicitacaoDAO().getDaSolicitacao(solicitacao)) {
                        texto += itensSolicitacao.getItensSolicitacaoPK().getProduto().getNome() + "; ";
                    }
                    texto += "\n";
                }
                Platform.runLater(() -> {
                    Dialog.notificationInformation("Há " + solicitacaos.size() + " solicitações pendentes.", texto);
                });
            } else {
                texto = "";
                Platform.runLater(() -> {
                    for (ItensSolicitacao itensSolicitacao : new ItensSolicitacaoDAO().getDaSolicitacao(solicitacaos.get(0))) {
                        texto += itensSolicitacao.getItensSolicitacaoPK().getProduto().getNome() + ": " + itensSolicitacao.getQuantidadeEntregue() + " itens.\n";
                    }
                    Dialog.notificationInformation("Solicitação " + solicitacaos.get(0).getStatus(), texto);
                });
            }
        }

    }

    class Evento extends TimerTask {

        @Override
        public void run() {
            notificacaoInstrutor();
        }

    }

}
