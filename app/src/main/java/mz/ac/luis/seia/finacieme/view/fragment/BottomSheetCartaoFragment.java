package mz.ac.luis.seia.finacieme.view.fragment;

import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.IconSpinnerAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentBottomSheetCartaoBinding;
import mz.ac.luis.seia.finacieme.databinding.FragmentBottomSheetDebitosBinding;
import mz.ac.luis.seia.finacieme.repository.ConfigFirebase;


public class BottomSheetCartaoFragment extends BottomSheetDialogFragment {
    FragmentBottomSheetCartaoBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BottomSheetCartaoFragment() {

    }

    public static BottomSheetCartaoFragment newInstance(String param1, String param2) {
        BottomSheetCartaoFragment fragment = new BottomSheetCartaoFragment();
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
        binding = FragmentBottomSheetCartaoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Drawable> icons = new ArrayList<>();
        ArrayAdapter<Drawable> adapter2 = new ArrayAdapter<Drawable>(getContext(), android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        icons.add(getDrawable(getContext(),R.drawable.ic_card));
        icons.add(getDrawable(getContext(),R.drawable.ic_porco));
        icons.add(getDrawable(getContext(),R.drawable.ic_account_balance));
        icons.add(getDrawable(getContext(),R.drawable.ic_ccount_));

        IconSpinnerAdapter adapter = new IconSpinnerAdapter(getContext(), icons);

        binding.spinnerIcones.setAdapter(adapter);

         final int[] COLORS = {
                Color.parseColor("#FF0000"), // Vermelho
                Color.parseColor("#FFA500"), // Laranja
                Color.parseColor("#FFFF00"), // Amarelo
                Color.parseColor("#008000"), // Verde
                Color.parseColor("#0000FF"), // Azul
                Color.parseColor("#4B0082"), // Índigo
                Color.parseColor("#EE82EE"), // Violeta
                Color.parseColor("#000000"), // Preto
                Color.parseColor("#FFC0CB"), // Rosa
                Color.parseColor("#800000"), // Borgonha
                Color.parseColor("#FF6347"), // Tomate
                Color.parseColor("#FFD700"), // Ouro
                Color.parseColor("#FF8C00"), // Escarlate
                Color.parseColor("#00FFFF"), // Ciano
                Color.parseColor("#800080"), // Roxo
                Color.parseColor("#00FF7F"), // Primavera verde
                Color.parseColor("#A9A9A9"), // Cinza
                Color.parseColor("#D2B48C"), // Marrom
                Color.parseColor("#48D1CC"), // Turquesa
                Color.parseColor("#8FBC8F"), // Verde mar
                Color.parseColor("#4682B4"), // Azul aço
                Color.parseColor("#ADFF2F"), // Verde lima
                Color.parseColor("#F08080"), // Rosa claro
                Color.parseColor("#B0C4DE"), // Azul claro
                Color.parseColor("#FFE4C4"), // Bege
                Color.parseColor("#FF69B4"), // Rosa profundo
                Color.parseColor("#7B68EE")};  // Azul ardósia

        binding.colorSeekbar.setMax(COLORS.length-1);
        binding.colorSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int colorIndex = progress % COLORS.length;
                int color = COLORS[colorIndex];
                Drawable selectedIcon = (Drawable)  binding.spinnerIcones.getSelectedItem();
                selectedIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                binding.spinnerIcones.setSelection(binding.spinnerIcones.getSelectedItemPosition());


                binding.buttonSalvarCartao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.ediSaldod.getText().toString();
                        binding.editNome.getText().toString();
                        Drawable selectedIcon = (Drawable)  binding.spinnerIcones.getSelectedItem();
                        Bitmap bitmap = ((BitmapDrawable) selectedIcon).getBitmap();
                        // cria uma referência ao local onde o Bitmap será armazenado no Firebase Storage
                        DatabaseReference firebase = ConfigFirebase.getFirebaseDataBase().child("icons").child("icone_" + System.currentTimeMillis() + ".png");;

                    }
                });


            }

            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private Drawable getDrawable(Context context, int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(resId, context.getTheme());
        } else {
            return context.getResources().getDrawable(resId);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}