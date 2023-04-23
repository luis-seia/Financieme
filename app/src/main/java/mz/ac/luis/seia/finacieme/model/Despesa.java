package mz.ac.luis.seia.finacieme.model;

public class Despesa extends  Movimentacao{
    private String Pagocom;
    public Despesa(String descricao, String categoria, String data, double valor) {
        super(descricao, categoria, data, valor);
    }
}
