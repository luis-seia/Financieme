package mz.ac.luis.seia.finacieme.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.IconSpinnerAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentBlottomSheetReceitaBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.helper.CustomItem;
import mz.ac.luis.seia.finacieme.helper.DataCustom;
import mz.ac.luis.seia.finacieme.model.Carteira;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;

public class BottomSheetReceitaFragment extends BottomSheetDialogFragment {
    FragmentBlottomSheetReceitaBinding binding;
    ArrayList<Carteira> carteiras = new ArrayList<>();
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private IconSpinnerAdapter adapterCarteira;
    private DatabaseReference carteiraRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    Carteira carteira;
    ArrayList<CustomItem> customItems = new ArrayList<>();;
    private double saldoTotal;
    private ValueEventListener valueEventListenerCarteira;

    public BottomSheetReceitaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        carteiraRef = firebaseRef.child("carteira").child(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBlottomSheetReceitaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String[] CATEGORIA = getResources().getStringArray(R.array.categorias_receitas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,CATEGORIA);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategoriaReceita.setAdapter(adapter);
        binding.editDataReceita.setText(DataCustom.currentData());
        adapterCarteira = new IconSpinnerAdapter(getContext(), customItems);
        if(customItems !=null){
            binding.spinnerContaAreceber.setAdapter(adapterCarteira);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        carteiraRef.removeEventListener(valueEventListenerCarteira);
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        carteiraRef.keepSynced(true);
        recuperarCarteira();
    }

    public  void recuperarCarteira(){
        valueEventListenerCarteira = carteiraRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customItems.clear();
                for (DataSnapshot dados : snapshot.getChildren()) {
                    Carteira carteira = dados.getValue(Carteira.class);
                    carteira.setKey(dados.getKey());
                    carteiras.add(carteira);
                    customItems.add(new CustomItem(carteira.getNome(),carteira.getIconResourceId()));
                }
                adapterCarteira.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}