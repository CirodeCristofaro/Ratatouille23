package com.INGSW2223_V_06.ratatouille23.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.AggiungiTavoloFragment;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoElementi;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MyAdapterElencoElementiDelTavolo extends RecyclerView.Adapter<MyAdapterElencoElementiDelTavolo.ViewHolderElementoTavolo> {
    private final Context context;
    private final ArrayList<DtoElementi> arrayList;
    private final AggiungiTavoloFragment aggiungiTavoloFragment;

    private final String TAG = "MyAdapterElencoElementiDelTavolo";


    public MyAdapterElencoElementiDelTavolo(Context context, ArrayList<DtoElementi> arrayList, AggiungiTavoloFragment aggiungiTavoloFragment) {
        this.context = context;
        this.arrayList = arrayList;
        this.aggiungiTavoloFragment = aggiungiTavoloFragment;
    }

    @NonNull
    @Override
    public ViewHolderElementoTavolo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_elemento_tavolo, parent, false);
        return new MyAdapterElencoElementiDelTavolo.ViewHolderElementoTavolo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderElementoTavolo holder, int position) {
        DtoElementi Elemento = arrayList.get(position);
        holder.nomeElemento.setText(Elemento.getElementoOrdinata().getNomeElemento());
        holder.prezzoElemento.setText(String.format("%s €",
                Elemento.getCosto().multiply(BigDecimal.valueOf(Elemento.getQuantita()))));
        holder.numeroTotDellaElemento.setText(String.valueOf(Elemento.getQuantita()));
        holder.aumentaNumeroElemento.setOnClickListener(v -> {
            int quantitaAumenta = Integer.parseInt(holder.numeroTotDellaElemento.getText().toString());
            quantitaAumenta++;
            Elemento.setQuantita(quantitaAumenta);
            holder.numeroTotDellaElemento.setText(String.valueOf(quantitaAumenta));
            arrayList.set(holder.getAdapterPosition(), Elemento);
            holder.prezzoElemento.setText(String.format("%s €",
                    Elemento.getCosto().multiply(BigDecimal.valueOf(quantitaAumenta))));
        });
        holder.diminuisciNumeroElemento.setOnClickListener(v -> {
            int quantaDiminuisce = Integer.parseInt(holder.numeroTotDellaElemento.getText().toString());
            quantaDiminuisce--;
            Elemento.setQuantita(quantaDiminuisce);
            if (quantaDiminuisce <= 0) {
                arrayList.remove(Elemento);
                notifyItemRemoved(holder.getAdapterPosition());
            } else {
                arrayList.set(holder.getAdapterPosition(), Elemento);
                holder.numeroTotDellaElemento.setText(String.valueOf(quantaDiminuisce));
            }
            holder.prezzoElemento.setText(String.format("%s €",
                    Elemento.getCosto().multiply(BigDecimal.valueOf(quantaDiminuisce))));
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolderElementoTavolo extends RecyclerView.ViewHolder {
        private final TextView nomeElemento;
        private final TextView prezzoElemento;
        private final TextView numeroTotDellaElemento;
        private final ImageButton diminuisciNumeroElemento;
        private final ImageButton aumentaNumeroElemento;
        private final CardView cardView;

        public ViewHolderElementoTavolo(@NonNull View itemView) {
            super(itemView);
            nomeElemento = itemView.findViewById(R.id.nomeElemento);
            prezzoElemento = itemView.findViewById(R.id.prezzoElemento);
            numeroTotDellaElemento = itemView.findViewById(R.id.numeroTotDegliElementi);
            cardView = itemView.findViewById(R.id.cardViewElementoTavolo);
            diminuisciNumeroElemento = itemView.findViewById(R.id.diminuisciNumeroElemento);
            aumentaNumeroElemento = itemView.findViewById(R.id.aumentaNumeroElemento);

        }
    }


}
