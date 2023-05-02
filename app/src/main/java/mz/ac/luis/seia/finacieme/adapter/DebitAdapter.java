package mz.ac.luis.seia.finacieme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.model.Divida;

public class DebitAdapter extends RecyclerView.Adapter<DebitAdapter.MyViewHolder> {


    private ArrayList<Divida> listDividas;
    public DebitAdapter(ArrayList<Divida> listDividas) {
        this.listDividas = listDividas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_recycler_debit, parent, false
        );
        return new DebitAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Divida divida = listDividas.get(position);

            holder.dataFim.setText(divida.getDataVencimento());
            holder.dataInicio.setText(divida.getDataContraida());
            holder.entidade.setText(divida.getEntidade());
            holder.valorDivida.setText(divida.getValor()+" MZN");
            holder.valorPago.setText(divida.getValorPago()+"/"+divida.getValor());

            int progresso = divida.getValorPago()==0? 0 : (int) ((divida.getValorPago() / divida.getValor()) * 100);

            holder.progressBar.setProgress(progresso);
            holder.taxaDejuro.setText("juros: "+divida.getJuros()+ " %");


    }

    @Override
    public int getItemCount() {
        return listDividas.size();
    }


    // inicio da Class viewHoler
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView entidade;
        TextView taxaDejuro;
        TextView valorDivida;
        TextView valorPago;
        ProgressBar progressBar;
        TextView dataInicio;
        TextView dataFim;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            entidade = itemView.findViewById(R.id.textEntidade);
            taxaDejuro = itemView.findViewById(R.id.textJuros);
            valorDivida = itemView.findViewById(R.id.textValorDivida);
            valorPago = itemView.findViewById(R.id.textValorPago);
            progressBar  = itemView.findViewById(R.id.progressBarDivida);
            dataInicio = itemView.findViewById(R.id.textDatainicio);
            dataFim  = itemView.findViewById(R.id.textDataFim);
        }
    }

}
