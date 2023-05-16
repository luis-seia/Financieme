package mz.ac.luis.seia.finacieme.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.ViewPagerAdapter;
import mz.ac.luis.seia.finacieme.databinding.ActivityLoginBinding;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;
import mz.ac.luis.seia.finacieme.view.fragment.LoginFragment;
import mz.ac.luis.seia.finacieme.view.fragment.RegistroFragment;

public class LoginActivity extends AppCompatActivity {
        ActivityLoginBinding binding;
        private FirebaseAuth auth;
        private User user;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewPager = findViewById(R.id.viewPager);

        // setting up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // add the fragments
        viewPagerAdapter.add(new LoginFragment(), "Iniciar sessão");
        viewPagerAdapter.add(new RegistroFragment(), "Criar conta");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
//
    }

}