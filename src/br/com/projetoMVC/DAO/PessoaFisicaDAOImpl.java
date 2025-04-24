package br.com.projetoMVC.DAO;
import br.com.projetoMVC.model.ConnectionFactory;
import br.com.projetoMVC.model.PessoaFisica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAOImpl implements GenericDAO {

    private static final String COMMAND_SQL_ERROR = "Problemas ao realizar comando SQL. Erro: ";
    private static final String CLOSED_CONNECTION_PROBLEM_ERROR = "Problemas ao fechar a conexão! Erro: ";
    public static final String INSERT_PESSOA_FISICA = "INSERT INTO PessoaFisica (nome, endereco, status, cpf, rg)" + "VALUES (?, ?, ?, ?, ?)";
    public static final String DELETE_FROM_PESSOA_FISICA = "DELETE FROM PessoaFisica WHERE id = ?";
    private static final String SELECT_PESSOA_FISICA = "SELECT * FROM PessoaFisica";
    private static final String UPDATE_PESSOA_FISICA = "UPDATE PessoaFisica SET nome = ?, status = ?, endereco = ?, cpf = ?, rg = ? WHERE id = ?";
    private static final String SELECT_FROM_PESSOA_FISICA_WHERE_CPF = "SELECT * FROM PessoaFisica WHERE cpf = ?";

    private Connection connection;

    public PessoaFisicaDAOImpl() throws Exception{
        try{
            this.connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com sucesso");
        }catch (Exception exception){
            throw new Exception(exception.getMessage());
        }
    }

    @Override //peretro
    public List<Object> Read() {
        List<Object> list = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            resultSet = connection.prepareStatement(SELECT_PESSOA_FISICA).executeQuery();
            while (resultSet.next()) {
                list.add(createPessoaFisica(resultSet));
            }
        } catch (SQLException e) {
            this.showException(e);
        } finally {
            this.closeConnection(connection);
        }

        return list;
    }

    @Override //ricardo
    public boolean insert(Object object) {
        PessoaFisica pessoaFisica = (PessoaFisica) object;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_PESSOA_FISICA);
            this.prepareQuery(statement, pessoaFisica);
            statement.execute();
            return true;
        } catch (SQLException exception) {
            this.showException(exception);
            return false;
        } finally {
            this.closeConnection(connection, statement);
        }
    }

    @Override //peretro
    public boolean update(Object object) {
        PessoaFisica pessoaFisica = (PessoaFisica) object;
        try {
            prepareQuery(connection.prepareStatement(UPDATE_PESSOA_FISICA), pessoaFisica);
            connection.prepareStatement(UPDATE_PESSOA_FISICA).execute();
            return true;
        } catch (SQLException e) {
            this.showException(e);
            return false;
        } finally {
            this.closeConnection(connection);
        }
    }

    @Override //ricardo
    public boolean delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_FROM_PESSOA_FISICA);
            statement.setInt(1,id);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception){
            this.showException(exception);
            return false;
        } finally {
            this.closeConnection(connection, statement);
        }

    }

    public PessoaFisica readCPF(String cpf) {
        PessoaFisica pessoaFisica = new PessoaFisica();
        ResultSet resultSet = null;
        String sql = SELECT_FROM_PESSOA_FISICA_WHERE_CPF;

        try {
            connection.prepareStatement(sql).setString(1, cpf);
            resultSet = connection.prepareStatement(sql).executeQuery();
            if (resultSet.next()) {
                pessoaFisica = createPessoaFisica(resultSet);
            }
        }  catch (SQLException e) {
            this.showException(e);
        } finally {
            this.closeConnection(connection, resultSet);
        }

        return pessoaFisica;
    }

    private void prepareQuery(PreparedStatement statement, PessoaFisica pessoaFisica) throws SQLException {
        statement.setString(1, pessoaFisica.getNome());
        statement.setString(2, pessoaFisica.getEndereco());
        statement.setBoolean(3, pessoaFisica.isStatus());
        statement.setString(4, pessoaFisica.getCpf());
        statement.setString(5, pessoaFisica.getRg());
    }

    private void closeConnection(Connection connection, ResultSet resultSet) {
        try {
            ConnectionFactory.closeConnection(connection, null, resultSet);
        } catch (Exception e) {
            System.out.println(CLOSED_CONNECTION_PROBLEM_ERROR + e.getMessage());
        }
    }

    private void closeConnection(Connection connection, Statement statement) {
        try {
            ConnectionFactory.closeConnection(connection, statement, null);
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

    private PessoaFisica createPessoaFisica(ResultSet resultSet) throws SQLException {
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setId(resultSet.getInt("id"));
        pessoaFisica.setNome(resultSet.getString("nome"));
        pessoaFisica.setStatus(resultSet.getBoolean("status"));
        pessoaFisica.setEndereco(resultSet.getString("endereco"));
        pessoaFisica.setCpf(resultSet.getString("cpf"));
        pessoaFisica.setRg(resultSet.getString("rg"));
        return pessoaFisica;
    }

    private void showException(SQLException ex) {
        System.out.println(COMMAND_SQL_ERROR + ex.getMessage());
        ex.printStackTrace();
    }
}
