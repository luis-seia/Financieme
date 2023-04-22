package mz.ac.luis.seia.finacieme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.model.Artigo;

public class LearnAdapter extends RecyclerView.Adapter<LearnAdapter.MyViewHolder> {
    private  ArrayList<Artigo> listaArtigos;
    public LearnAdapter(ArrayList<Artigo> listaArtigos) {
        this.listaArtigos = listaArtigos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_learn, parent, false
        );
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Artigo artigo = listaArtigos.get(position);

        holder.capitulo.setText("Capitulo " +artigo.getCapitulo());
        holder.titulo.setText(artigo.getTitulo()+"");
    }

    @Override
    public int getItemCount() {
        return listaArtigos.size();
    }


    // inicio da Class viewHoler
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView capitulo;
        TextView titulo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            capitulo = itemView.findViewById(R.id.capitulo);
        }
    }

}
