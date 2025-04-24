package br.com.projetoMVC.model;

public class PessoaJuridica extends Pessoa{
    private String cnpj;
    private boolean capitalAberto;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public boolean isCapitalAberto() {
        return capitalAberto;
    }

    public void setCapitalAberto(boolean capitalAberto) {
        this.capitalAberto = capitalAberto;
    }

    void consultarViaCNPJ(){

    }
}

