package mz.ac.luis.seia.finacieme.model;

public abstract class Movimentacao {
    private String descricao;
    private String categoria;
    private String data;
    private double valor;

    public Movimentacao(String descricao, String categoria, String data, double valor) {
        this.descricao = descricao;
        this.categoria = categoria;
        this.data = data;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
