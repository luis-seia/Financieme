package mz.ac.luis.seia.finacieme.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class MainActivity extends IntroActivity {
    private FirebaseAuth auth;
    private DatabaseReference firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        verificarUsuarioLogado();
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro2).build());
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro3).build());
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro_registro).canGoBackward(false) .canGoForward(false).build());

    }

    public void Entrar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }


    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void verificarUsuarioLogado(){
        auth = ConfigFirebase.getAuth();
        if(auth.getCurrentUser() !=null){
            openMasterActivity();
            finish();
        }
    }
    public void openMasterActivity(){
        startActivity(new Intent(this, MasterActivity.class));
    }
}