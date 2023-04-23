package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.ac.luis.seia.finacieme.databinding.FragmentNewTransictionBinding;


public class NewTransictionFragment extends Fragment {
    FragmentNewTransictionBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewTransictionFragment() {

    }



    public static NewTransictionFragment newInstance(String param1, String param2) {
        NewTransictionFragment fragment = new NewTransictionFragment();
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


        binding = FragmentNewTransictionBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.buttonAdicionarReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetReceitaFragment bottomSheetReceitaFragment = new BottomSheetReceitaFragment();
                bottomSheetReceitaFragment.show(getFragmentManager(), "");
            }
        });

        binding.ButomAcionarDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonSheetDespesasFragment buttonSheetDespesasFragment = new ButtonSheetDespesasFragment();
                buttonSheetDespesasFragment.show(getFragmentManager(), "");
            }
        });

        binding.buttonTranferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetTransferenciasFragment bottomSheetTransferenciasFragment = new BottomSheetTransferenciasFragment();
                bottomSheetTransferenciasFragment.show(getFragmentManager(), "");
            }
        });

        binding.buttonAddDebitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDebitosFragment bottomSheetDebitosFragment = new BottomSheetDebitosFragment();
                bottomSheetDebitosFragment.show(getFragmentManager(), "");
            }
        });
    }
}