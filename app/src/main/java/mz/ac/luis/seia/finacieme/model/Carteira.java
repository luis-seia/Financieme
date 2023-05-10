package mz.ac.luis.seia.finacieme.model;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;

public class Carteira {
    private String nome;
    private Double saldo;
    private String tipo;
    private int iconResourceId;
    private String key;



    public int getIconResourceId() {
        return iconResourceId;
    }

    public Carteira() {
    }

    public Carteira(String nome, Double saldo, String tipo, int iconResourceId) {
        this.nome = nome;
        this.saldo = saldo;
        this.tipo = tipo;
        this.iconResourceId = iconResourceId;
    }

    public Carteira(int iconResourceId, String nome, Double saldo) {
        this.iconResourceId = iconResourceId;
        this.nome = nome;
        this.saldo = saldo;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        firebase.child("carteira")
                .child(userId)
                .push()
                .setValue(this,new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            // Ocorreu um erro ao salvar os dados

                        } else {
                            // Os dados foram salvos com sucesso

                        }
                    }
                });


    }
}
