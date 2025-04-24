package br.com.projetoMVC.controller;

import br.com.projetoMVC.DAO.GenericDAO;
import br.com.projetoMVC.DAO.PessoaFisicaDAOImpl;
import br.com.projetoMVC.model.PessoaFisica;

public class PessoaFisicaController {

    public void insert(PessoaFisica pf){
        try{
            GenericDAO dao = new PessoaFisicaDAOImpl();
            if(dao.insert(pf)){
                System.out.println("Produto cadastrado com sucesso");
            }

        }catch (Exception exception){
            System.out.println("Problemas ao cadastrar produto! Erro: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void delete(int id){
        try{
            GenericDAO dao = new PessoaFisicaDAOImpl();
            dao.delete(id);
        }catch (Exception exception){
            System.out.println("Problemas ao excluir produto! Erro: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}
