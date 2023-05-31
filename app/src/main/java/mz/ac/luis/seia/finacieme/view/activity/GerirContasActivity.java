package mz.ac.luis.seia.finacieme.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.CartaoAdapter;
import mz.ac.luis.seia.finacieme.databinding.ActivityGerirContasBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.model.Carteira;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class GerirContasActivity extends AppCompatActivity {
    ArrayList<Carteira> carteiras = new ArrayList<>();
    CartaoAdapter cartaoAdapter;
    private DatabaseReference carteiraRef;
    Carteira carteira;
    private float saldoTotal;
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private DatabaseReference userRef;
    private ValueEventListener valueEventListenerCarteira;
    private ValueEventListener valueEventListenerUser;
    private AppBarConfiguration appBarConfiguration;
    private ActivityGerirContasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGerirContasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);

        cartaoAdapter = new CartaoAdapter(carteiras);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this , LinearLayout.VERTICAL));
        binding.recyclerView.setAdapter(cartaoAdapter);

        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        userRef = ConfigFirebase.getUserRef();
        carteiraRef = firebaseRef.child(ConfigFirebase.carteriasNo()).child(userId);

    }

    public  void recuperarCarteira(){
        valueEventListenerCarteira = carteiraRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carteiras.clear();
                for(DataSnapshot dados: snapshot.getChildren()){
                    Carteira carteira = dados.getValue(Carteira.class);
                    carteira.setKey(dados.getKey());
                    carteiras.add(carteira);
                }
                cartaoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void swipe(){
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirConta(viewHolder);
            }
        };
        new ItemTouchHelper(callback).attachToRecyclerView(binding.recyclerView);
    }

    public void excluirConta(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Excluir divida");
        alertDialog.setTitle("Deseja excluir a conta?");
        alertDialog.setCancelable(false);


        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int position = viewHolder.getAdapterPosition();
                carteira = carteiras.get(position);
                carteiraRef.child(carteira.getKey()).removeValue();
                cartaoAdapter.notifyItemRemoved(position);
                cartaoAdapter.notifyDataSetChanged();
                atualizarSaldo();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(GerirContasActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                cartaoAdapter.notifyDataSetChanged();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public  void atualizarSaldo(){
        saldoTotal-= carteira.getSaldo();
        userRef.child("saldoTotal").setValue(saldoTotal);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.back : onBackPressed();break;
        }
        return true;
    }
}