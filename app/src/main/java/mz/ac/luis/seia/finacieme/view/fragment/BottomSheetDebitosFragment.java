package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.FragmentBlottomSheetReceitaBinding;
import mz.ac.luis.seia.finacieme.databinding.FragmentBottomSheetDebitosBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.helper.DataCustom;
import mz.ac.luis.seia.finacieme.model.Divida;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class BottomSheetDebitosFragment extends BottomSheetDialogFragment {

    FragmentBottomSheetDebitosBinding binding;
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private Double debitoTotal;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BottomSheetDebitosFragment() {

    }


    public static BottomSheetDebitosFragment newInstance(String param1, String param2) {
        BottomSheetDebitosFragment fragment = new BottomSheetDebitosFragment();
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
        binding = FragmentBottomSheetDebitosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        recuperarDividaTotal();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.editDatainicio.setText(DataCustom.currentData());


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
                                        clear();
                                        dismiss();

                                    }catch (NumberFormatException e){
                                        Toast.makeText(getContext(), "Insira um digito valido", Toast.LENGTH_SHORT).show();
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
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(userId);

        usuarioRef.addValueEventListener(new ValueEventListener() {
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
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(userId);

        usuarioRef.child("debitoTotal").setValue(divida);
    }


    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}