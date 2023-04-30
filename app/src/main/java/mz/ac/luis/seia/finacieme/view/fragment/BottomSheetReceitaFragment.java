package mz.ac.luis.seia.finacieme.view.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.FragmentBlottomSheetReceitaBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetReceitaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetReceitaFragment extends BottomSheetDialogFragment {
    FragmentBlottomSheetReceitaBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BottomSheetReceitaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomSheetReceitaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomSheetReceitaFragment newInstance(String param1, String param2) {
        BottomSheetReceitaFragment fragment = new BottomSheetReceitaFragment();
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
        binding = FragmentBlottomSheetReceitaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        final String[] operacoes = {"Abrir website", "efectuar ligacao", "abrir maps em uma determinada llocation", "abrir camera e tirar fotografia"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, operacoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPagocom.setAdapter(adapter);

        return view;
    }
}