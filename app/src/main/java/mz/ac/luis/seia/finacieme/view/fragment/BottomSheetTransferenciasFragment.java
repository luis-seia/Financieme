package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.adapter.IconSpinnerAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentBottomSheetTransferenciasBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.helper.CustomItem;
import mz.ac.luis.seia.finacieme.helper.DateCustom;
import mz.ac.luis.seia.finacieme.model.Carteira;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class BottomSheetTransferenciasFragment extends BottomSheetDialogFragment {
FragmentBottomSheetTransferenciasBinding binding;
    ArrayList<Carteira> carteiras = new ArrayList<>();
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private IconSpinnerAdapter adapterCarteira;
    private DatabaseReference carteiraRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    Carteira carteira;
    ArrayList<CustomItem> customItems = new ArrayList<>();;
    private double saldoTotal;
    private ValueEventListener valueEventListenerCarteira;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetTransferenciasBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        carteiraRef = firebaseRef.child(ConfigFirebase.carteriasNo()).child(userId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.editData.setText(DateCustom.currentData());
        adapterCarteira = new IconSpinnerAdapter(getContext(), customItems);
        if(customItems !=null){
            binding.spinnerContaDestino.setAdapter(adapterCarteira);
            binding.spinnerContaOrigem.setAdapter(adapterCarteira);
        }
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
}