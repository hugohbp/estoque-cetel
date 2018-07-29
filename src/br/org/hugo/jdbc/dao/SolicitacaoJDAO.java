/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.hugo.jdbc.dao;

import br.org.senai.dao.StatusDAO;
import br.org.senai.dao.UsuarioDAO;
import br.org.senai.entities.Solicitacao;
import br.org.senai.entities.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo
 */
public class SolicitacaoJDAO extends GenericDAO<Solicitacao> {

    @Override
    public int count() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void salvar(Solicitacao t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Solicitacao> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Solicitacao> pegarSolicitacoesInstrutorNotificacao(Usuario usuario) {
        List<Solicitacao> solicitacaos = new ArrayList<>();
        try {
            String query = "select * from Solicitacao where status_id in (?, ?) and usuario_id=?";
            preparedStatement = this.con.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 7);
            preparedStatement.setInt(3, usuario.getId());
            resultSet = preparedStatement.executeQuery();
            System.out.println("foi");
            while (resultSet.next()) {
                Solicitacao solicitacao = new Solicitacao();
                solicitacao.setId(resultSet.getInt("id"));
                solicitacao.setAprovadoSupervisao(resultSet.getBoolean("aprovadoSupervisao"));
                solicitacao.setAtivo(resultSet.getBoolean("ativo"));
                solicitacao.setCancelada(resultSet.getBoolean("cancelada"));
                solicitacao.setDataDevolucao(resultSet.getDate("dataDevolucao"));
                solicitacao.setDataEntrega(resultSet.getDate("dataEntrega"));
                solicitacao.setDataNecessidade(resultSet.getDate("dataNecessidade"));
                solicitacao.setDataPrevistaDevolucao(resultSet.getDate("dataPrevistaDevolucao"));
                solicitacao.setEntregueManutencao(resultSet.getBoolean("entregueManutencao"));
                solicitacao.setStatus(new StatusDAO().get(resultSet.getInt("status_id")));
                solicitacao.setUsuario(new UsuarioDAO().get(resultSet.getInt("usuario_id")));

                solicitacaos.add(solicitacao);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SolicitacaoJDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(preparedStatement);
            DbUtil.close(con);
        }
        return solicitacaos;
    }

}
