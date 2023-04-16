package mz.ac.luis.seia.finacieme.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

import mz.ac.luis.seia.finacieme.R;


public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);



        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder().background(android.R.color.white).canGoBackward(false).fragment(R.layout.intro1).build());
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro2).build());
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro3).build());
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro_registro).canGoForward(false) .canGoForward(false).build());

    }

    public void Entrar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void Cadastrar(View view){
        startActivity(new Intent(this, RegistroActivity.class));
    }
}