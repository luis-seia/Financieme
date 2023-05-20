package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.IconSpinnerAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentButtonSheetDespesasBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.helper.CustomItem;
import mz.ac.luis.seia.finacieme.helper.DateCustom;
import mz.ac.luis.seia.finacieme.model.Carteira;
import mz.ac.luis.seia.finacieme.model.Movimentacao;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class ButtonSheetDespesasFragment extends BottomSheetDialogFragment {
    private FragmentButtonSheetDespesasBinding binding;
    private ArrayList<Carteira> carteiras = new ArrayList<>();
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private IconSpinnerAdapter adapterCarteira;
    private DatabaseReference carteiraRef;
    private DatabaseReference userRef;
    private ValueEventListener valueEventListenerUser;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private Carteira carteira;
    private String categoria;
    private ArrayList<CustomItem> customItems = new ArrayList<>();;
    private double saldoTotal;
    private double despesaTotal;
    private ValueEventListener valueEventListenerCarteira;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        carteiraRef = firebaseRef.child(ConfigFirebase.carteriasNo()).child(userId);
        userRef = ConfigFirebase.getUserRef();
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
        binding.editData.setText(DateCustom.currentData());
        adapterCarteira = new IconSpinnerAdapter(getContext(), customItems);
        if(customItems !=null){
            binding.spinnerPagocom.setAdapter(adapterCarteira);
        }

        binding.spinnerCategoriaDespesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    categoria = CATEGORIA[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public  void recuperarCarteira(){
        valueEventListenerCarteira = carteiraRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customItems.clear();
                carteiras.clear();
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
        userRef.removeEventListener(valueEventListenerUser);
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        carteiraRef.keepSynced(true);
        recuperarCarteira();
        recuperarDespesaTotal();
        binding.spinnerPagocom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                carteira = carteiras.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.buttonaSalvarDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarDespesa();
            }
        });
    }

    public boolean isFilds(){
        boolean checked = false;
        if(!binding.editValorDespesa.getText().toString().isEmpty()){
            if (!binding.editDescricaoDespesa.getText().toString().isEmpty()){
                if (!binding.editData.getText().toString().isEmpty()){
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

    public void salvarDespesa(){
        if(isFilds()){
            try{
                String descricao = binding.editDescricaoDespesa.getText().toString();
                double valor = Double.parseDouble(binding.editValorDespesa.getText().toString());
                String data= binding.editData.getText().toString();
                double saldoActual = carteira.getSaldo();
                String conta = carteira.getNome();
                String tipo = "d";
                Movimentacao movimentacao = new Movimentacao(descricao,categoria,valor,data,tipo,conta) ;
                movimentacao.salvar(data);
                double despesa = valor + despesaTotal;
                actualizarDespesaTotal(despesa);
                double saldo = saldoTotal - valor;
                actualizarSaldoTotal(saldo);
                actualizarSaldoCarteira(saldoActual-valor);
                Toast.makeText(getContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
            }catch (NumberFormatException e){
                Toast.makeText(getContext(), "Insira um valor Valido", Toast.LENGTH_SHORT).show();
            }catch (DatabaseException e){
                Toast.makeText(getContext(), "Erro ao salvar", Toast.LENGTH_SHORT).show();
            }    finally {
                dismiss();
            }
        }
    }

    public void recuperarDespesaTotal(){
   valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
           User user =  snapshot.getValue(User.class);
           assert user != null;
           despesaTotal = user.getDespesaTotal();
           saldoTotal = user.getSaldoTotal();
       }

       @Override
       public void onCancelled(@NonNull DatabaseError error) {

       }
   });
    }

    public void actualizarDespesaTotal(double despesa){
        userRef.child(ConfigFirebase.despesaTotalNo()).setValue(despesa);
    }

    public void actualizarSaldoTotal(Double saldo){
        userRef.child(ConfigFirebase.saldoTotalNo()).setValue(saldo);
    }

    public void actualizarSaldoCarteira(double valor){
        carteiraRef.child(carteira.getKey()).child("saldo").setValue(valor);
    }



}