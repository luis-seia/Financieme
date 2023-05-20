package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import mz.ac.luis.seia.finacieme.databinding.FragmentBottomSheetDebitosBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.helper.DateCustom;
import mz.ac.luis.seia.finacieme.model.Divida;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class BottomSheetDebitosFragment extends BottomSheetDialogFragment {

    FragmentBottomSheetDebitosBinding binding;
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    DatabaseReference userRef;
    private ValueEventListener valueEventListenerUser;
    private Double debitoTotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetDebitosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        recuperarDividaTotal();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRef = ConfigFirebase.getUserRef();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.editDatainicio.setText(DateCustom.currentData());


        binding.buttonaSalvarDivida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.editEntidadeDivida.getText().toString().isEmpty()){
                    if(!binding.editDataVencimento.getText().toString().isEmpty()){
                        if(!binding.editValor.getText().toString().isEmpty()){
                            if(!binding.editTaxaDeJuros.getText().toString().isEmpty() ){
                                if(!binding.editDatainicio.getText().toString().isEmpty()){
                                    try {
                                        String entidade = binding.editEntidadeDivida.getText().toString();
                                        String dataContracao = binding.editDatainicio.getText().toString();
                                        String dataVencimento = binding.editDataVencimento.getText().toString();
                                        double montante = Double.parseDouble(binding.editValor.getText().toString());
                                        float juros = Float.parseFloat(binding.editTaxaDeJuros.getText().toString());
                                        Divida divida = new Divida(entidade,montante,dataContracao,dataVencimento,juros);
                                        double dividaActualizada = montante + debitoTotal;
                                        atualizarDivida(dividaActualizada);
                                        divida.salvar();
                                        Toast.makeText(getContext(), "gravado com sucesso", Toast.LENGTH_LONG).show();

                                    }catch (NumberFormatException e){
                                        Toast.makeText(getContext(), "Insira um digito valido", Toast.LENGTH_LONG).show();
                                    }catch (DatabaseException e){
                                        Toast.makeText(getContext(), "Erro ao connectar", Toast.LENGTH_LONG).show();
                                    }catch (Exception e){
                                        Toast.makeText(getContext(), "Erro ao connectar", Toast.LENGTH_LONG).show();
                                    }finally {
                                        clear();
                                        dismiss();
                                    }
                                }else {
                                    Toast.makeText(getContext(), "Preencha a data de vencimento", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getContext(), "Preencha a taxa de juros", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "Preencha o valor", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Preencha a data da contracao da divida", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Preencha o nome da entidade", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void clear(){
        binding.editEntidadeDivida.setText("");
        binding.editTaxaDeJuros.setText("");
        binding.editDataVencimento.setText("");
         binding.editValor.setText("");
        Toast.makeText(getContext(), "gravado com sucesso", Toast.LENGTH_SHORT).show();
    }

    public void recuperarDividaTotal(){
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
         userRef = firebaseRef.child("usuarios").child(userId);

        valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                debitoTotal = user.getDebitoTotal();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void atualizarDivida(Double divida){

        userRef.child(ConfigFirebase.debitoTotalNo()).setValue(divida);
    }


    public void onDestroyView() {
        super.onDestroyView();
        userRef.removeEventListener(valueEventListenerUser);
        binding = null;
    }


}