package mz.ac.luis.seia.finacieme.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;

public class User {
    private String idUser;
    private String nome;
    private String email;
    private String senha;
    private double receitaTotal = 0.00;
    private double despesaTotal = 0.00;
    private double saldoTotal = 0.00;
    private double debitoTotal = 0.00;

    public User() {
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public double getDebitoTotal() {
        return debitoTotal;
    }

    public void setDebitoTotal(double debitoTotal) {
        this.debitoTotal = debitoTotal;
    }

    public double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    public User(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public User(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    @Exclude
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void saveFromFirebase(){
        DatabaseReference firebase = ConfigFirebase.getFirebaseDataBase();
        firebase.child("usuarios")
                .child(this.idUser)
                .setValue(this);
    }
}
