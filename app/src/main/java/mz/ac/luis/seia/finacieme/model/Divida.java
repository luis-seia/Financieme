package mz.ac.luis.seia.finacieme.model;

public class Divida {
    private String entidade;
    private double valor;
    private String dataContraida;
    private String dataVencimento;
    private float juros;
    private double valorPago;

    private String estado;

    public Divida(String entidade, double valor, String dataContraida, String dataVencimento, float juros, double valorPago) {
        this.entidade = entidade;
        this.valor = valor;
        this.dataContraida = dataContraida;
        this.dataVencimento = dataVencimento;
        this.juros = juros;
        this.valorPago = valorPago;

    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataContraida() {
        return dataContraida;
    }

    public void setDataContraida(String dataContraida) {
        this.dataContraida = dataContraida;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public float getJuros() {
        return juros;
    }

    public void setJuros(float juros) {
        this.juros = juros;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }


}
