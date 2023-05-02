package mz.ac.luis.seia.finacieme.model;

public class ContaDeCredito extends Carteira{

    private Long numeroCartao;

    public ContaDeCredito(String icone, String nome, Double saldo, Long numeroCartao) {
        super(icone, nome, saldo);
        this.numeroCartao = numeroCartao;
    }
}
