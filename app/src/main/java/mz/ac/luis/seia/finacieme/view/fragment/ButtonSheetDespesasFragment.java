package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.ArrayAdapterDespesas;
import mz.ac.luis.seia.finacieme.adapter.IconSpinnerAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentButtonSheetDespesasBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.helper.CustomItem;
import mz.ac.luis.seia.finacieme.helper.DataCustom;
import mz.ac.luis.seia.finacieme.model.Carteira;
import mz.ac.luis.seia.finacieme.model.Movimentacao;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class ButtonSheetDespesasFragment extends BottomSheetDialogFragment {
    FragmentButtonSheetDespesasBinding binding;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        carteiraRef = firebaseRef.child("carteira").child(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentButtonSheetDespesasBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String[] CATEGORIA = getResources().getStringArray(R.array.categorias_despesas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,CATEGORIA);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategoriaDespesa.setAdapter(adapter);
        binding.editData.setText(DataCustom.currentData());
        adapterCarteira = new IconSpinnerAdapter(getContext(), customItems);
        if(customItems !=null){
            binding.spinnerPagocom.setAdapter(adapterCarteira);
        }

        binding.buttonaSalvarDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spinnerPagocom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        carteira = carteiras.get(i);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        binding.buttonaSalvarDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFilds()){
                    try{
                        String descricao = binding.editDescricaoDespesa.getText().toString();
                        double valor = Double.parseDouble(binding.editValorDespesa.getText().toString());
                        String data= binding.editData.getText().toString();
                        Double saldoActual = carteira.getSaldo();

                        Movimentacao movimentacao = new Movimentacao() ;
                        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
                        carteiraRef = firebaseRef.child("carteira").child(userId);
                        carteiraRef.child(carteira.getKey()).removeValue();
                    }catch (NumberFormatException e){

                    }catch (DatabaseException e){
                        Toast.makeText(getContext(), "Erro ao salvar", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

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

    public boolean isFilds(){
        boolean checked = false;
        if(binding.editValorDespesa.getText().toString().isEmpty()){
            if (binding.editDescricaoDespesa.getText().toString().isEmpty()){
                if (binding.editData.getText().toString().isEmpty()){
                    checked = true;
                }else{
                    Toast.makeText(getContext(), "Preencha todos campos", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), "Preencha todos campos", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Preencha todos campos", Toast.LENGTH_SHORT).show();
        }
        return checked;
    }
}