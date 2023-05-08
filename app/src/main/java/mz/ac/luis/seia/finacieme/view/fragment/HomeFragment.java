package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.CartaoAdapter;
import mz.ac.luis.seia.finacieme.adapter.DebitAdapter;
import  mz.ac.luis.seia.finacieme.databinding.FragmentHomeBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.model.Carteira;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    ArrayList<Carteira> carteiras = new ArrayList<>();
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private DatabaseReference userRef;
    private DatabaseReference dividaRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private Double debitoTotal;
    private ValueEventListener valueEventListenerUser;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createGraphic();
        this.adicionarCartoes();
        CartaoAdapter cartaoAdapter = new CartaoAdapter(carteiras);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewCartoes.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCartoes.setHasFixedSize(true);
        binding.recyclerViewCartoes.addItemDecoration(new DividerItemDecoration(getContext() , LinearLayout.VERTICAL));
        binding.recyclerViewCartoes.setAdapter(cartaoAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarDividaTotal();
    }

    public void onStop() {
        super.onStop();
        userRef.removeEventListener(valueEventListenerUser);
    }

    public void createGraphic(){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(24545, "Receita"));
        pieEntries.add(new PieEntry(54545, "Despesa"));
        pieEntries.add(new PieEntry(35454, "Saldo"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Descricao");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieDataSet.setValueTextSize(13f);


        PieData pieData = new PieData(pieDataSet);
        binding.grafico.setData(pieData);
        binding.grafico.getDescription().setEnabled(false);
        binding.grafico.animate();

    }
    public void adicionarCartoes(){
        Carteira carteira = new Carteira(getResources().getDrawable(R.drawable.ic_account_balance),"Standard bank",20000d);
        carteiras.add(carteira);

        carteira = new Carteira(getResources().getDrawable(R.drawable.ic_ccount_),"Millenium Bim",84000d);
        carteiras.add(carteira);

        carteira = new Carteira(getResources().getDrawable(R.drawable.ic_card),"BCI",65000d);
        carteiras.add(carteira);
    }

    public void recuperarDividaTotal() {
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        userRef = firebaseRef.child("usuarios").child(userId);
        valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                debitoTotal = user.getDebitoTotal();
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String ressult = decimalFormat.format(debitoTotal);
                binding.textDebit.setText("-"+ressult);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}