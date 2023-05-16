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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.ActivityLoginBinding;
import mz.ac.luis.seia.finacieme.databinding.FragmentLoginBinding;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;
import mz.ac.luis.seia.finacieme.view.activity.MasterActivity;

public class LoginFragment extends Fragment {
FragmentLoginBinding binding;

    private FirebaseAuth auth;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

                binding.progressBar.setVisibility(View.GONE);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !binding.editLoginEmail.getText().toString().isEmpty()){
                    if (!binding.editLoginSenha.getText().toString().isEmpty()){
                        user = new User(binding.editLoginEmail.getText().toString(), binding.editLoginSenha.getText().toString());
                        validateUser(user);
                    }else{
                        Toast.makeText(getContext(), "preencha o email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Preencha a senha", Toast.LENGTH_SHORT).show();
                }
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
                            Toast.makeText(getContext(), excecao, Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void openMasterActivity(){
        startActivity(new Intent(getActivity(), MasterActivity.class));
    }
}