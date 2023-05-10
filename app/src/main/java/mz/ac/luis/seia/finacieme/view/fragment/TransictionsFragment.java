package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.databinding.FragmentTransictionsBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.model.Divida;
import mz.ac.luis.seia.finacieme.model.Movimentacao;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class TransictionsFragment extends Fragment {
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private DatabaseReference userRef;
    private DatabaseReference movimentacaoRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private Double debitoTotal;
    private ValueEventListener valueEventListenerUser;
    private ValueEventListener valueEventListenerMov;
    FragmentTransictionsBinding binding;
    ArrayList<Movimentacao> movimentacoes = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        movimentacaoRef = firebaseRef.child("movimentacao").child(userId);
        userRef = firebaseRef.child("usuarios").child(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransictionsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    public  void recuperarMovimentacao(){
        valueEventListenerMov = movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movimentacoes.clear();
                for(DataSnapshot dados: snapshot.getChildren()){
                    Divida divida = dados.getValue(Divida.class);
                    divida.setKey(dados.getKey());
                    listDividas.add(divida);
                }
                debitAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}