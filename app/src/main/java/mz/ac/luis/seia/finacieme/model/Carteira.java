package mz.ac.luis.seia.finacieme.model;

import android.graphics.drawable.Drawable;

public class Carteira {
    private String icone;
    private String nome;
    private Double saldo;
    private String tipo;
    private Drawable icon;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Carteira(Drawable icon, String nome, Double saldo) {
        this.icon = icon;
        this.nome = nome;
        this.saldo = saldo;
    }

    public String getIcone() {
        return icone;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
