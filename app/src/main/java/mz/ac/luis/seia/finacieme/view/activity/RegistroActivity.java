package mz.ac.luis.seia.finacieme.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.ActivityRegistroBinding;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;

public class RegistroActivity extends AppCompatActivity {
    private ActivityRegistroBinding bindingRegistro;
    private User user;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        bindingRegistro = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(bindingRegistro.getRoot());

        bindingRegistro.buttonRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(validationFields()){
                        user = new User(bindingRegistro.editNomeRegistar.getText().toString(), bindingRegistro.editEmailRegistar.getText().toString(), bindingRegistro.editSenhaRegistar.getText().toString());
                        saveUser(user);
                    }
            }
        });

        bindingRegistro.textViewFalcaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public boolean validationFields(){
        boolean validation = false;
        if( !bindingRegistro.editNomeRegistar.getText().toString().isEmpty()){
            if (!bindingRegistro.editEmailRegistar.getText().toString().isEmpty()){
                if(!bindingRegistro.editSenhaRegistar.getText().toString().isEmpty()){
                    validation = true;
                }else{
                    Toast.makeText(RegistroActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegistroActivity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(RegistroActivity.this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }
        return validation;
    }

    public void saveUser(User user){
        auth = ConfigFirebase.getAuth();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getSenha()).addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful()){
                    startActivity(new Intent(RegistroActivity.this, MasterActivity.class));
                    finish();
                }else{
                    String textExcecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        textExcecao = "digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        textExcecao = "digite um email valido";
                    }catch (FirebaseAuthUserCollisionException e){
                        textExcecao = "Esta conta ja foi cadastrada";
                    } catch (Exception e) {
                        textExcecao = "Erro ao cadastrar usuario";
                        e.printStackTrace();
                    }
                    Toast.makeText(RegistroActivity.this, textExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}