package mz.ac.luis.seia.finacieme.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Adapter;

import com.joanzapata.pdfview.PDFView;

import java.io.File;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.databinding.FragmentLearnBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LearnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnFragment extends Fragment {
    private FragmentLearnBinding binding;
        // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LearnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearnFragment.
     */
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setHasFixedSize(true);
        //binding.recyclerView.setAdapter();
    }
}