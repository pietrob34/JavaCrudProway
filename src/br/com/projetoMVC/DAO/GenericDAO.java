package br.com.projetoMVC.DAO;

import java.util.List;

public interface GenericDAO {
    public List<Object> Read();
    public boolean insert(Object object);
    public boolean update(Object object);
    public boolean delete(int id);
}
