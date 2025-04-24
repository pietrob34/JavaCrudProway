package br.com.projetoMVC.DAO;

import br.com.projetoMVC.model.ConnectionFactory;
import br.com.projetoMVC.model.PessoaJuridica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAOImpl implements GenericDAO {

    private static final String INSERT_INTO_PESSOA_JURIDICA_VALUES = "INSERT INTO PessoaJuridica(nome, status, endereco, cnpj, capital_aberto) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_PESSOA_JURIDICA = "UPDATE PessoaJuridica SET nome = ?, status = ?, endereco = ?, cnpj = ?, capital_aberto = ? WHERE id = ?";
    private static final String DELETE_FROM_PESSOA_JURIDICA = "DELETE FROM PessoaJuridica WHERE id = ?";
    private static final String SELECT_PESSOA_JURIDICA_ALL = "SELECT * FROM PessoaJuridica";
    private static final String SELECT_FROM_PESSOA_JURIDICA_WHERE_CNPJ = "SELECT * FROM PessoaJuridica WHERE cnpj = ?";
    private static final String CLOSED_CONNECTION_PROBLEM_ERROR = "Problemas ao fechar a conexão! Erro: ";
    private static final String COMMAND_SQL_ERROR = "Problemas ao realizar comando SQL. Erro: ";

    private final Connection connection;

    public PessoaJuridicaDAOImpl() throws Exception {
        try {
            this.connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com sucesso");
        } catch (Exception exception){
            throw new Exception(exception.getMessage());
        }
    }

    @Override //peretro
    public List<Object> Read() {
        List<Object> list = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            resultSet = connection.prepareStatement(SELECT_PESSOA_JURIDICA_ALL).executeQuery();
            while (resultSet.next()) {
                list.add(createPJ(resultSet));
            }
        } catch (SQLException e) {
            this.showException(e);
        } finally {
            this.closeConnection(resultSet);
        }

        return list;
    }

    @Override //spike
    public boolean insert(Object object) {
        PessoaJuridica pessoaJuridica = (PessoaJuridica) object;
        String sql = INSERT_INTO_PESSOA_JURIDICA_VALUES;

        try {
            sql = prepareQuery(sql,connection, pessoaJuridica);
            connection.prepareStatement(sql).execute();
            return true;
        } catch (SQLException e) {
            this.showException(e);
            return false;
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override //peretro
    public boolean update(Object object) {
        PessoaJuridica pessoaJuridica = (PessoaJuridica) object;
        String sql = UPDATE_PESSOA_JURIDICA;

        try {
            sql = prepareQuery(sql,connection, pessoaJuridica);
            connection.prepareStatement(sql).execute();
            return true;
        } catch (SQLException e) {
            this.showException(e);
            return false;
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override //spike
    public boolean delete(int id) {
        String sql = DELETE_FROM_PESSOA_JURIDICA;
        try {
            connection.prepareStatement(sql).setInt(1, id);
            connection.prepareStatement(sql).execute();
            return true;
        } catch (SQLException e) {
            this.showException(e);
            return false;
        } finally {
            this.closeConnection(connection);
        }
    }

    //peretro
    public PessoaJuridica readCNPJ(String cnpj) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        ResultSet resultSet = null;
        String sql = SELECT_FROM_PESSOA_JURIDICA_WHERE_CNPJ;

        try {
            connection.prepareStatement(sql).setString(1, cnpj);
            resultSet = connection.prepareStatement(sql).executeQuery();
            if (resultSet.next()) {
                pessoaJuridica = createPJ(resultSet);
            }
        }  catch (SQLException e) {
            this.showException(e);
        } finally {
            this.closeConnection(resultSet);
        }

        return pessoaJuridica;
    }

    private void closeConnection(ResultSet resultSet) {
        try {
            ConnectionFactory.closeConnection(connection, null, resultSet);
        } catch (Exception e) {
            System.out.println(CLOSED_CONNECTION_PROBLEM_ERROR + e.getMessage());
        }
    }

    private void closeConnection(Connection connection) {
        try {
            ConnectionFactory.closeConnection(connection, null, null);
        } catch (Exception e) {
            System.out.println(CLOSED_CONNECTION_PROBLEM_ERROR + e.getMessage());
        }
    }

    private PessoaJuridica createPJ(ResultSet resultSet) throws SQLException {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setId(resultSet.getInt("id"));
        pessoaJuridica.setNome(resultSet.getString("nome"));
        pessoaJuridica.setStatus(resultSet.getBoolean("status"));
        pessoaJuridica.setEndereco(resultSet.getString("endereco"));
        pessoaJuridica.setCnpj(resultSet.getString("cnpj"));
        pessoaJuridica.setCapitalAberto(resultSet.getBoolean("capital_aberto"));
        return pessoaJuridica;
    }

    private String prepareQuery(String sql, Connection connection, PessoaJuridica pessoaJuridica) throws SQLException{
        connection.prepareStatement(sql).setString(1, pessoaJuridica.getNome());
        connection.prepareStatement(sql).setBoolean(2, pessoaJuridica.isStatus());
        connection.prepareStatement(sql).setString(3, pessoaJuridica.getEndereco());
        connection.prepareStatement(sql).setString(4, pessoaJuridica.getCnpj());
        connection.prepareStatement(sql).setBoolean(5, pessoaJuridica.isCapitalAberto());

        if(sql.contains("id = ?")) {
            connection.prepareStatement(sql).setInt(6, pessoaJuridica.getId());
        }

        return sql;
    }

    private void showException(SQLException ex) {
        System.out.println(COMMAND_SQL_ERROR + ex.getMessage());
        ex.printStackTrace();
    }
}