package mz.ac.luis.seia.finacieme.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.model.Movimentacao;

public class MovimentacaoAdapter extends RecyclerView.Adapter<MovimentacaoAdapter.MyViewHolder> {
    ArrayList<Movimentacao> movimentacoes = new ArrayList<>();
    Context context;

    public MovimentacaoAdapter(ArrayList<Movimentacao> movimentacoes, Context context) {
        this.movimentacoes = movimentacoes;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_movimentacoes, parent, false
        );
        return new MovimentacaoAdapter.MyViewHolder(itemLista);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movimentacao movimentacao = movimentacoes.get(position);
        holder.valor.setText(String.valueOf(movimentacao.getValor()));
        holder.descricao.setText(movimentacao.getDescricao());
        holder.categoria.setText(movimentacao.getCategoria());
        holder.conta.setText(movimentacao.getConta());
        holder.data.setText(movimentacao.getData());

        if (movimentacao.getTipo().equals("d")){
            holder.valor.setTextColor(android.R.color.holo_red_light);
            holder.valor.setText("-" +movimentacao.getValor());
        }
    }

    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView valor;
        TextView conta;
        TextView categoria;
        TextView descricao;
        TextView data;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            valor =itemView.findViewById(R.id.textViewValorMov);
            conta =itemView.findViewById(R.id.textviewContaMov);
            descricao =itemView.findViewById(R.id.textViewTituloMov);
            categoria =itemView.findViewById(R.id.textViewCategoriaMov);
            data =itemView.findViewById(R.id.textViewDataMov);
        }
    }
}
