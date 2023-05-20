package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.ac.luis.seia.finacieme.databinding.FragmentNewTransictionBinding;


public class NewTransictionFragment extends Fragment {
    FragmentNewTransictionBinding binding;



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

        binding.buttonAdicionarCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetCartaoFragment bottomSheetCartaoFragment = new BottomSheetCartaoFragment();
                bottomSheetCartaoFragment.show(getFragmentManager(), "4");
            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}