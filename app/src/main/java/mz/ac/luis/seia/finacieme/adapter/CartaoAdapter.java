package mz.ac.luis.seia.finacieme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.model.Carteira;

public class CartaoAdapter extends RecyclerView.Adapter<CartaoAdapter.MyViewHolder> {


    ArrayList<Carteira> carteiras = new ArrayList<>();

    public CartaoAdapter(ArrayList<Carteira> carteiras) {
        this.carteiras = carteiras;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_cartoes, parent, false
        );
        return new CartaoAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Carteira carteira = carteiras.get(position);
            holder.nome.setText(carteira.getNome());
            holder.saldo.setText(""+carteira.getSaldo());
            holder.imageView.setImageDrawable(carteira.getIcon());
    }

    @Override
    public int getItemCount() {
        return carteiras.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView saldo;
        TextView nome;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            imageView = itemView.findViewById(R.id.imageviewCartso);
            saldo = itemView.findViewById(R.id.textViewSaldoConta);
            nome = itemView.findViewById(R.id.textViewCartao);
        }
    }
}
