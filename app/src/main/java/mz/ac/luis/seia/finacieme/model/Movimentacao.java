package mz.ac.luis.seia.finacieme.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.helper.DateCustom;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;

public class Movimentacao {
    private String descricao;
    private String categoria;
    private String data;
    private double valor;
    private String tipo;
    private String conta;

    public Movimentacao(String descricao, String categoria,double valor, String data,  String tipo, String conta) {
        this.descricao = descricao;
        this.categoria = categoria;
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
        this.conta = conta;
    }

    public Movimentacao() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void salvar(String data){
        FirebaseAuth auth = ConfigFirebase.getAuth();
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        DatabaseReference firebase = ConfigFirebase.getFirebaseDataBase();
        String mesAno = DateCustom.mesAno(data);
        firebase.child("movimentacao")
                .child(userId)
                .child(mesAno)
                .push()
                .setValue(this);


    }
}
