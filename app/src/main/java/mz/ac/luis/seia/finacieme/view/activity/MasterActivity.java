package mz.ac.luis.seia.finacieme.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.ActivityMasterBinding;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;
import mz.ac.luis.seia.finacieme.view.fragment.AboutAppFragment;
import mz.ac.luis.seia.finacieme.view.fragment.DebitFragment;
import mz.ac.luis.seia.finacieme.view.fragment.HomeFragment;
import mz.ac.luis.seia.finacieme.view.fragment.LearnFragment;
import mz.ac.luis.seia.finacieme.view.fragment.NewTransictionFragment;
import mz.ac.luis.seia.finacieme.view.fragment.TransictionsFragment;

public class MasterActivity extends AppCompatActivity {
    private ActivityMasterBinding binding;
    private  FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_master);
        binding = ActivityMasterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Financie.me");
        setSupportActionBar(toolbar );

        //Carregar  configuracao do fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPagerager, new HomeFragment()).commit(); // fragment default

        ConfigBottomNavegationView(); //chamar metodo para configuracao

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId()){
            case R.id.menu_sair:
                //logoutUser();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case R.id.menu_sobre:
                fragmentTransaction.replace(R.id.viewPagerager, new AboutAppFragment()).commit();break;
            case R.id.menu_definicoes:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://luis-seia.github.io/Politica-de-privacidade/")));break;
            case R.id.menu_suporte:
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.putExtra(intent.EXTRA_EMAIL, new String[]{"luis.seia@gmail.com"});
                intent.putExtra(intent.EXTRA_SUBJECT, "Relatar problema");
                intent.putExtra(intent.EXTRA_TEXT, "Mensagem automatica");
                intent.setType("message/rfc822");
                startActivity(intent);break;

        }
        return super.onOptionsItemSelected(item);
    }

   public void logoutUser(){
        try{
            FirebaseAuth.getInstance().signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
   }

   private void ConfigBottomNavegationView(){
       BottomNavigationView bottomNavigationView = findViewById(R.id.bnve);
        habilityNavegation(bottomNavigationView);

   }

  public void habilityNavegation(BottomNavigationView view){

        view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()){
                    case R.id.ic_home:
                        fragmentTransaction.replace(R.id.viewPagerager, new HomeFragment()).commit();
                        return true;
                    case R.id.ic_add:
                        fragmentTransaction.replace(R.id.viewPagerager, new NewTransictionFragment()).commit();
                        return true;
                    case R.id.ic_aprender:
                        fragmentTransaction.replace(R.id.viewPagerager, new LearnFragment()).commit();
                        return true;
                    case R.id.ic_debitos:
                        fragmentTransaction.replace(R.id.viewPagerager, new DebitFragment()).commit();
                        return true;
                    case R.id.ic_transicoes:
                        fragmentTransaction.replace(R.id.viewPagerager, new TransictionsFragment()).commit();
                        return true;

                }


                return false;
            }
        });

  }

}