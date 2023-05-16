package mz.ac.luis.seia.finacieme.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.FragmentRegistroBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;
import mz.ac.luis.seia.finacieme.view.activity.LoginActivity;
import mz.ac.luis.seia.finacieme.view.activity.MasterActivity;
import mz.ac.luis.seia.finacieme.view.activity.RegistroActivity;


public class RegistroFragment extends Fragment {
FragmentRegistroBinding bindingRegistro;
    private User user;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindingRegistro = FragmentRegistroBinding.inflate(getLayoutInflater());
        return bindingRegistro.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingRegistro.buttonRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validationFields()){
                    user = new User(bindingRegistro.editNomeRegistar.getText().toString(), bindingRegistro.editEmailRegistar.getText().toString(), bindingRegistro.editSenhaRegistar.getText().toString());
                    saveUser(user);
                }
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
                    Toast.makeText(getContext(), "Preencha a senha", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), "Preencha o email", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Preencha o nome", Toast.LENGTH_SHORT).show();
        }
        return validation;
    }

    public void saveUser(User user){
        auth = ConfigFirebase.getAuth();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getSenha()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful()){
                    String idUser = Base64Custom.codificarBase64(user.getEmail());
                    user.setIdUser(idUser);
                    user.saveFromFirebase();
                    startActivity(new Intent(getContext(), MasterActivity.class));

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
                    Toast.makeText(getContext(), textExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}