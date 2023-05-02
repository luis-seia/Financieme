package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.DebitAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentDebitBinding;
import mz.ac.luis.seia.finacieme.databinding.FragmentLearnBinding;
import mz.ac.luis.seia.finacieme.model.Divida;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebitFragment extends Fragment {
    FragmentDebitBinding binding;
    private ArrayList<Divida> listDividas;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDebitBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();
        listDividas = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.adicionadrDividas();
        DebitAdapter debitAdapter = new DebitAdapter(listDividas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewDebito.setLayoutManager(linearLayoutManager);
        binding.recyclerViewDebito.setHasFixedSize(true);
        binding.recyclerViewDebito.addItemDecoration(new DividerItemDecoration(getContext() , LinearLayout.VERTICAL));
        binding.recyclerViewDebito.setAdapter(debitAdapter);



    }

    public void adicionadrDividas(){
        Divida divida = new Divida("ISUTC",23455,"21/04/2022/","21/05/2023",5.4f,12340);
        listDividas.add(divida);

        divida = new Divida("Tia de petisco",234,"1/04/2023/","21/06/2023/",0,0);
        listDividas.add(divida);

        divida = new Divida("M-pesa",1000,"1/04/2023/","21/06/2023/",2,0);
        listDividas.add(divida);
    }
}