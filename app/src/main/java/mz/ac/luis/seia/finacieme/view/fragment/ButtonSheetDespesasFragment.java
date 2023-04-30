package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.ArrayAdapterDespesas;
import mz.ac.luis.seia.finacieme.databinding.FragmentButtonSheetDespesasBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ButtonSheetDespesasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonSheetDespesasFragment extends BottomSheetDialogFragment {
    FragmentButtonSheetDespesasBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ButtonSheetDespesasFragment() {

    }



    public static ButtonSheetDespesasFragment newInstance(String param1, String param2) {
        ButtonSheetDespesasFragment fragment = new ButtonSheetDespesasFragment();
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

        binding = FragmentButtonSheetDespesasBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        String[] valores = getResources().getStringArray(R.array.categorias_despesas);
        int[] icons = getResources().getIntArray(R.array.meu_array_icons);
       // ArrayAdapterDespesas adapter = new ArrayAdapterDespesas(getContext(), R.layout.item_spinner_despesas, valores, icons);

    //    binding.spinnerCategoriaDespesa.setAdapter(adapter);
    }
}