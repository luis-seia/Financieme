package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.FragmentButtonSheetDespesasBinding;
import mz.ac.luis.seia.finacieme.databinding.FragmentTransictionsBinding;
import mz.ac.luis.seia.finacieme.model.Movimentacao;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class TransictionsFragment extends Fragment {
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private DatabaseReference userRef;
    private DatabaseReference dividaRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private Double debitoTotal;
    private ValueEventListener valueEventListenerUser;
    FragmentTransictionsBinding binding;
    ArrayList<Movimentacao> movimentacoes = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransictionsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

}