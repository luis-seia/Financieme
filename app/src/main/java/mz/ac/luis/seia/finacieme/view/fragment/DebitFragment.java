package mz.ac.luis.seia.finacieme.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.DebitAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentDebitBinding;
import mz.ac.luis.seia.finacieme.databinding.FragmentLearnBinding;
import mz.ac.luis.seia.finacieme.helper.Base64Custom;
import mz.ac.luis.seia.finacieme.model.Divida;
import mz.ac.luis.seia.finacieme.model.User;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebitFragment extends Fragment {
    FragmentDebitBinding binding;
    private ArrayList<Divida> listDividas = new ArrayList<>();
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDataBase();
    private DatabaseReference userRef;
    private DatabaseReference dividaRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private Double debitoTotal;
    private ValueEventListener valueEventListenerUser;
    private ValueEventListener valueEventListenerDividas;
    private      DebitAdapter debitAdapter;
    private Divida divida;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DebitFragment() {

    }


    public static DebitFragment newInstance(String param1, String param2) {
        DebitFragment fragment = new DebitFragment();
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
    public void onStart() {
        super.onStart();
        recuperarDivida();
        recuperarDividaTotal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDebitBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        debitAdapter = new DebitAdapter(listDividas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewDebito.setLayoutManager(linearLayoutManager);
        binding.recyclerViewDebito.setHasFixedSize(true);
        binding.recyclerViewDebito.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        binding.recyclerViewDebito.setAdapter(debitAdapter);
        swipe();
        return view;
    }


    public void recuperarDividaTotal() {
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        userRef = firebaseRef.child("usuarios").child(userId);
       valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                debitoTotal = user.getDebitoTotal();
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String ressult = decimalFormat.format(debitoTotal);
                binding.textViewDebitoTotal.setText("-"+ressult);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        userRef.removeEventListener(valueEventListenerUser);
        dividaRef.removeEventListener(valueEventListenerDividas);
    }


    public  void recuperarDivida(){
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        dividaRef = firebaseRef.child("divida").child(userId);
        valueEventListenerDividas = dividaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDividas.clear();
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

    public void swipe(){
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirMovimentacao(viewHolder);
            }
        };
        new ItemTouchHelper(callback).attachToRecyclerView(binding.recyclerViewDebito);
    }

    public void excluirMovimentacao(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Excluir divida");
        alertDialog.setTitle("Deseja excluir a divida?");
        alertDialog.setCancelable(false);


        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int position = viewHolder.getAdapterPosition();
                divida = listDividas.get(position);
                String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
                dividaRef = firebaseRef.child("divida").child(userId);
                dividaRef.child(divida.getKey()).removeValue();
                debitAdapter.notifyItemRemoved(position);
                atualizarDivida();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                debitAdapter.notifyDataSetChanged();

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void atualizarDivida(){
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        userRef = firebaseRef.child("usuarios").child(userId);

        debitoTotal-= divida.getValor();
        userRef.child("debitoTotal").setValue(debitoTotal);
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}