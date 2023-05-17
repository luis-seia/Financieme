package mz.ac.luis.seia.finacieme.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.CartaoAdapter;
import  mz.ac.luis.seia.finacieme.databinding.FragmentHomeBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.helper.CustomItemDecoration;
import mz.ac.luis.seia.finacieme.model.Carteira;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    ArrayList<Carteira> carteiras = new ArrayList<>();
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private DatabaseReference userRef;
    private DatabaseReference dividaRef;
    private DatabaseReference carteiraRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    Carteira carteira;
    private float  receitaTotal;
    private float despesaTotal;
    private double debitoTotal;
    private float saldoTotal;
    private PieChart chart;
    private ValueEventListener valueEventListenerCarteira;
    private ValueEventListener valueEventListenerUser;
    CartaoAdapter cartaoAdapter;
    ArrayList<PieEntry> pieEntries = new ArrayList<>();
    PieDataSet pieDataSet;
    PieData pieData;
    public HomeFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        userRef = firebaseRef.child("usuarios").child(userId);
        carteiraRef = firebaseRef.child("carteira").child(userId);
        dividaRef = firebaseRef.child("usuarios").child(userId);
        recuperarSaldoTotal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        swipe();
        chart = view.findViewById(R.id.grafico);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartaoAdapter = new CartaoAdapter(carteiras);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewCartoes.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCartoes.setHasFixedSize(true);
        binding.recyclerViewCartoes.addItemDecoration(new DividerItemDecoration(getContext() , LinearLayout.VERTICAL));
        binding.recyclerViewCartoes.setAdapter(cartaoAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        userRef.keepSynced(true);
        carteiraRef.keepSynced(true);
        dividaRef.keepSynced(true);
        recuperarCarteira();
        recuperarDividaTotal();
    }

    public void onStop() {
        super.onStop();
        carteiraRef.removeEventListener(valueEventListenerCarteira);
        userRef.removeEventListener(valueEventListenerUser);
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


    public void recuperarDividaTotal() {

        valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        debitoTotal = user.getDebitoTotal();
                        DecimalFormat decimalFormat = new DecimalFormat("0.##");
                         String ressult = decimalFormat.format(debitoTotal);
                         binding.textDebit.setText("-"+ressult);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void recuperarSaldoTotal() {
        valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot ) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        receitaTotal = (float) user.getReceitaTotal();
                        Log.i("TAG", String.valueOf(receitaTotal));
                        saldoTotal = (float) user.getSaldoTotal();
                        despesaTotal =(float) user.getDespesaTotal();
                        DecimalFormat decimalFormat = new DecimalFormat("0.##");
                        String result = decimalFormat.format(saldoTotal);
                        binding.textSaldo.setText(result);

                    }
                }
                configGraphic();
                chart.notifyDataSetChanged();
                chart.invalidate();
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
        new ItemTouchHelper(callback).attachToRecyclerView(binding.recyclerViewCartoes);
    }

    public void excluirConta(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Excluir divida");
        alertDialog.setTitle("Deseja excluir a conta?");
        alertDialog.setCancelable(false);


        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int position = viewHolder.getAdapterPosition();
                carteira = carteiras.get(position);
                String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
                carteiraRef = firebaseRef.child("carteira").child(userId);
                carteiraRef.child(carteira.getKey()).removeValue();
                cartaoAdapter.notifyItemRemoved(position);
                cartaoAdapter.notifyDataSetChanged();
                atualizarSaldo();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
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

    public void configGraphic(){
        pieEntries.add(new PieEntry(receitaTotal, "Receita"));
        pieEntries.add(new PieEntry(despesaTotal, "Despesa"));
        pieEntries.add(new PieEntry(saldoTotal, "Saldo"));

        pieDataSet = new PieDataSet(pieEntries, "Descricao");
        pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(13f);
        chart.setData(pieData);
        chart.getDescription().setEnabled(false);
        chart.animate();
    }

}