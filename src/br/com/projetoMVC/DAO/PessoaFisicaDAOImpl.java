package br.com.projetoMVC.DAO;
import br.com.projetoMVC.model.ConnectionFactory;
import br.com.projetoMVC.model.PessoaFisica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PessoaFisicaDAOImpl implements GenericDAO{
    private Connection connection;

    public PessoaFisicaDAOImpl() throws Exception{
        try{
            this.connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com sucesso");
        }catch (Exception exception){
            throw new Exception(exception.getMessage());
        }
    }

    @Override
    public List<Object> Read() {
        return List.of();
    }

    @Override //ricardo
    public boolean insert(Object object) {
        PessoaFisica pF = (PessoaFisica) object;
        PreparedStatement statement = null;
        String sql = "INSERT INTO pessoaFisica (nome, endereco, status, cpf, rg)" + "VALUES (?, ?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, pF.getNome());
            statement.setString(2, pF.getEndereco());
            statement.setBoolean(3, pF.isStatus());
            statement.setString(4, pF.getCpf());
            statement.setString(5, pF.getRg());
            statement.execute();
            return true;
        }catch (SQLException exception){
            System.out.println("Problemas na DAO ao cadastrar Pessoa fisica! Erro: " + exception.getMessage());
            exception.printStackTrace();
            return false;
        } finally {
            try{
                ConnectionFactory.closeConnection(connection, statement);
            }catch (Exception exception){
                System.out.println("Problemas ao fechar conexão! Erro: " + exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(Object object) {
        return false;
    }

    @Override //ricardo
    public boolean delete(int id) {
        PreparedStatement statement = null;
        String sql = "DELETE FROM pessoaFisica WHERE id = ?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
            return true;
        }catch (SQLException exception){
            System.out.println("Problemas na DAO ao excluir pessoa fisica" + exception.getMessage());
            exception.printStackTrace();
            return false;
        }finally {
            try{
                ConnectionFactory.closeConnection(connection,statement);
            }catch (Exception exception){
                System.out.println("Problemas ao fechar conexão! Erro: " + exception.getMessage());
                exception.printStackTrace();
            }
        }

    }

    public void readCPF(){

    }
}
