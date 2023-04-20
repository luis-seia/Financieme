package mz.ac.luis.seia.finacieme.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.ActivityLoginBinding;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;

public class LoginActivity extends AppCompatActivity {
        ActivityLoginBinding binding;
        private FirebaseAuth auth;
        private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setVisibility(View.GONE);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !binding.editLoginEmail.getText().toString().isEmpty()){
                    if (!binding.editLoginSenha.getText().toString().isEmpty()){
                        user = new User(binding.editLoginEmail.getText().toString(), binding.editLoginSenha.getText().toString());
                        validateUser(user);
                    }else{
                        Toast.makeText(LoginActivity.this, "preencha o email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.textViewRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
                finish();
            }
        });
    }

    public void validateUser(User user){
        binding.progressBar.setVisibility(View.VISIBLE);
        auth = ConfigFirebase.getAuth();
        auth.signInWithEmailAndPassword(user.getEmail(),user.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            binding.progressBar.setVisibility(View.GONE);
                            openMasterActivity();
                            finish();
                        }else{
                            String excecao = "";
                            try {
                                throw   task.getException();
                            }catch (FirebaseAuthInvalidCredentialsException | FirebaseAuthInvalidUserException e){
                                excecao = "Email ou senhas invalidos";
                            } catch (Exception e) {
                                excecao = "erro ao efectuar Login";
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void openMasterActivity(){
        startActivity(new Intent(this, MasterActivity.class));
    }


}