package mz.ac.luis.seia.finacieme.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import mz.ac.luis.seia.finacieme.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutAppFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutAppFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutAppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutAppFragment newInstance(String param1, String param2) {
        AboutAppFragment fragment = new AboutAppFragment();
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
        // force enable bright mode
      final String descricao = "Acreditamos que o gerenciamento financeiro pessoal deve ser fácil e acessível para todos. Por isso, " +
              "nosso aplicativo oferece uma variedade de recursos para ajudar os usuários a monitorar suas despesas, planejar seus orçamentos e acompanhar seus investimentos." +
                " Esperamos que você aproveite nosso aplicativo e se beneficie de suas funcionalidades. Se tiver alguma dúvida ou feedback, não hesite em nos contatar.";
      Element versao = new Element();
       versao.setTitle(" Versao 1.0");

       return new AboutPage(getContext())
               .setImage(R.mipmap.ic_launcher_round)
                .setDescription(descricao)

                .addGroup("Entre em contacto")
                .addEmail("luis.seiax@gmail.com", "Envie um email ")
                .addWebsite("https://gitlab.com/users/sign_in", "Acesse nosso site")

                .addGroup("Redes Sociais")
                .addGitHub("Luis-Seia", "GitHub")
                .addInstagram("luis.seia_", "Instagram")
                .addTwitter("Luis-Seia", "Twitter")

                .addItem(versao)
                .create();

    }
}