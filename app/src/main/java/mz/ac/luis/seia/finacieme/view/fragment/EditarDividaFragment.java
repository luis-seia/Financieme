package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.FragmentEditarDividaBinding;


public class EditarDividaFragment extends BottomSheetDialogFragment {
private FragmentEditarDividaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentEditarDividaBinding.inflate(getLayoutInflater());
       return binding.getRoot();
    }

}