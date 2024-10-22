package mz.ac.luis.seia.finacieme.view.fragment;

import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.IconSpinnerAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentBottomSheetCartaoBinding;
import mz.ac.luis.seia.finacieme.databinding.FragmentBottomSheetDebitosBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.helper.CustomItem;
import mz.ac.luis.seia.finacieme.model.Carteira;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class BottomSheetCartaoFragment extends BottomSheetDialogFragment {
    FragmentBottomSheetCartaoBinding binding;
    ArrayList<CustomItem> customItems;
    private double SaldoTotal;
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private DatabaseReference userRef;
    private ValueEventListener valueEventListenerUser;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public BottomSheetCartaoFragment() {

    }

    public static BottomSheetCartaoFragment newInstance(String param1, String param2) {
        BottomSheetCartaoFragment fragment = new BottomSheetCartaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRef = ConfigFirebase.getUserRef();
    }

    @Override
    public void onStart() {
        super.onStart();
        userRef.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBottomSheetCartaoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        recuperarSaldoTotal();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customItems = getCustomItems();
        IconSpinnerAdapter adapter = new IconSpinnerAdapter(getContext(), customItems);


        if(customItems !=null){
            binding.spinnerIcones.setAdapter(adapter);
        }

        binding.buttonSalvarCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.ediSaldod.getText().toString().isEmpty()){
                    if(!binding.editNome.getText().toString().isEmpty()){
                        try{
                            double saldo = Double.parseDouble(binding.ediSaldod.getText().toString());
                            String nome = binding.editNome.getText().toString();
                            CustomItem customItem = (CustomItem) binding.spinnerIcones.getSelectedItem();
                            Drawable icon = getDrawable(getContext(),customItem.getSpinnerIconImage());

                            int iconResourceId =  customItem.getSpinnerIconImage();
                            String tipo = binding.radioButtonCartao.isChecked()? "cartao":"conta";
                            Carteira carteira = new Carteira(nome,saldo,tipo,iconResourceId);
                            carteira.salvar();
                            double SaldoActualizado = saldo + SaldoTotal;
                            atualizarSaldo(SaldoActualizado);
                            dismiss();
                            Toast.makeText(getContext(), "salvo Com sucesso", Toast.LENGTH_LONG).show();
                        }catch (NumberFormatException e ){
                            Toast.makeText(getContext(), "insira um valor valido", Toast.LENGTH_LONG).show();
                        }catch (DatabaseException e){
                            Toast.makeText(getContext(), "Erro ao connectar", Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Toast.makeText(getContext(), "Erro ao connectar", Toast.LENGTH_LONG).show();
                        }finally {
                            dismiss();
                        }
                    }else{
                        Toast.makeText(getContext(), "Preencha o nome do cartao", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Preencha o valor do saldo disponivel", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private ArrayList<CustomItem> getCustomItems() {
        customItems = new ArrayList<>();
        customItems.add(new CustomItem("BNI",R.drawable.bni));
        customItems.add(new CustomItem("Mpesa",R.drawable.mpesa));
        customItems.add(new CustomItem("My money",R.drawable.my_money));
        customItems.add(new CustomItem("Ponto 24",R.drawable.ponto_24));
        customItems.add(new CustomItem("Standard Bank",R.drawable.standard));
        customItems.add(new CustomItem("Confre",R.drawable.ic_porco));
        customItems.add(new CustomItem("Outros",R.drawable.ic_account_balance));
        customItems.add(new CustomItem("Reserva",R.drawable.ic_ccount_));
        customItems.add(new CustomItem("Outras Carteiras",R.drawable.ic_card));
        return customItems;
    }

    private Drawable getDrawable(Context context, int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(resId, context.getTheme());
        } else {
            return context.getResources().getDrawable(resId);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        userRef.removeEventListener(valueEventListenerUser);
        binding = null;
    }

    public void recuperarSaldoTotal(){
        valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                SaldoTotal = user.getSaldoTotal();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Falha na conexao. \n certifique-se que esta ligo a internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void atualizarSaldo(Double saldo){
        userRef.child("saldoTotal").setValue(saldo);
    }






}