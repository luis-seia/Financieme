package mz.ac.luis.seia.finacieme.model;

import android.graphics.drawable.Drawable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;

public class Carteira {
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

    public Carteira() {
    }

    public Carteira(String nome, Double saldo, String tipo, Drawable icon) {
        this.nome = nome;
        this.saldo = saldo;
        this.tipo = tipo;
        this.icon = icon;
    }

    public Carteira(Drawable icon, String nome, Double saldo) {
        this.icon = icon;
        this.nome = nome;
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public void salvar(){
        FirebaseAuth auth = ConfigFirebase.getAuth();
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        DatabaseReference firebase = ConfigFirebase.getFirebaseDataBase();
        firebase.child("carteria")
                .child(userId)
                .push()
                .setValue(this);
    }
}
