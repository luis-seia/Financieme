package mz.ac.luis.seia.finacieme.model;

public class Carteira {
    private String icone;
    private String nome;
    private Double saldo;

    public Carteira(String icone, String nome, Double saldo) {
        this.icone = icone;
        this.nome = nome;
        this.saldo = saldo;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
