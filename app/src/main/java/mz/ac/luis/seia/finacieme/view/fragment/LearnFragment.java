package mz.ac.luis.seia.finacieme.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.adapter.LearnAdapter;
import mz.ac.luis.seia.finacieme.databinding.FragmentLearnBinding;
import mz.ac.luis.seia.finacieme.model.Artigo;
import mz.ac.luis.seia.finacieme.model.RecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LearnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnFragment extends Fragment {
    private FragmentLearnBinding binding;
    private ArrayList<Artigo> listaArtigos = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LearnFragment() {

    }



    // TODO: Rename and change types and number of parameters
    public static LearnFragment newInstance(String param1, String param2) {
        LearnFragment fragment = new LearnFragment();
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
        binding = FragmentLearnBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.criarArtigos();
        LearnAdapter learnAdapter = new LearnAdapter(listaArtigos);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext() , LinearLayout.VERTICAL));
        binding.recyclerView.setAdapter(learnAdapter);

        binding.recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(),
                        binding.recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }));
    }

    public void criarArtigos(){
        Artigo artigo = new Artigo(1, "Administracao financeira");
        listaArtigos.add(artigo);

        artigo = new Artigo(3, "Estrutura das financas pessoais");
        listaArtigos.add(artigo);

        artigo = new Artigo(4, "Orçamento Pessoal ou Familiar");
        listaArtigos.add(artigo);

        artigo = new Artigo(5, "Diagnostico da situacao financiera");
        listaArtigos.add(artigo);

        artigo = new Artigo(2, "Gestao de financas pessoais");
        listaArtigos.add(artigo);

        artigo = new Artigo(6, "Uso do Crédito e Administração das Dívidas");
        listaArtigos.add(artigo);

        artigo = new Artigo(7, "Consumo Planejado e Consciente");
        listaArtigos.add(artigo);

        artigo = new Artigo(8, "Poupança e Investimento");
        listaArtigos.add(artigo);

        artigo = new Artigo(9, "Planejamento financiero ");
        listaArtigos.add(artigo);

    }
}