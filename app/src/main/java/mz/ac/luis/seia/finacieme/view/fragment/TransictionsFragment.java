package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import mz.ac.luis.seia.finacieme.adapter.MovimentacaoAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentTransictionsBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.model.Divida;
import mz.ac.luis.seia.finacieme.model.Movimentacao;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class TransictionsFragment extends Fragment {
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private DatabaseReference userRef;
    private DatabaseReference movimentacaoRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private Double  receitaTotal ;
    MovimentacaoAdapter movimentacaoAdapter;
    private Double  saldoTotal  ;
    private Double despesaTotal;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         movimentacaoAdapter = new MovimentacaoAdapter(movimentacoes, getContext());
    }

    //
    @Override
    public void onStart() {
        super.onStart();
        // Mantém os dados sincronizados, mesmo quando o dispositivo estiver offline
        movimentacaoRef.keepSynced(true);
        userRef.keepSynced(true);
        recuperarMovimentacao();
    }

    public  void recuperarMovimentacao(){
        valueEventListenerMov = movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movimentacoes.clear();
                for(DataSnapshot dados: snapshot.getChildren()){
                    Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                    assert movimentacao != null;
                    movimentacao.setKey(dados.getKey());
                    movimentacoes.add(movimentacao);
                }
                movimentacaoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void recuperarTotal(){
        valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                receitaTotal = user.getReceitaTotal();
                saldoTotal = user.getSaldoTotal();
                despesaTotal = user.getDespesaTotal();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}