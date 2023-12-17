package com.INGSW2223_V_06.ratatouille23.recyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.AggiungiElementoAlTavoloFragment;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoElementi;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class myAdapterAggiungiElementoAlTavolo extends RecyclerView.Adapter<myAdapterAggiungiElementoAlTavolo.ViewHolderAggiungiElementoAlTavolo> {
    private final Context context;
    private List<DtoElementi> arrayList;
    private final AggiungiElementoAlTavoloFragment aggiungiElementoAlTavoloFragment;

    private final String TAG = "myAdapterAggiungiElementoAlTavolo";


    public myAdapterAggiungiElementoAlTavolo(Context context, List<DtoElementi> arrayList,
                                             AggiungiElementoAlTavoloFragment aggiungiElementoAlTavoloFragment) {
        this.context = context;
        this.arrayList = arrayList;
        this.aggiungiElementoAlTavoloFragment = aggiungiElementoAlTavoloFragment;
    }

    @NonNull
    @Override
    public ViewHolderAggiungiElementoAlTavolo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_elemento_tavolo, parent, false);
        return new ViewHolderAggiungiElementoAlTavolo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAggiungiElementoAlTavolo holder, int position) {
        DtoElementi Elemento = arrayList.get(position);
        if (Elemento.getQuantita() != 0) {
            Log.i(TAG, "onBindViewHolder: " + Elemento);
            holder.nomeElemento.setText(Elemento.getElementoOrdinata().getNomeElemento());

            holder.prezzoElemento.setText(String.format(Locale.getDefault(), "%.2f €",
                    Elemento.getCosto().multiply(BigDecimal.valueOf(Elemento.getQuantita()))));
            holder.numeroTotDegliElementi.setText(String.valueOf(Elemento.getQuantita()));
            holder.aumentaNumeroElementi.setOnClickListener(v -> {
                int quantitaAumenta = Integer.parseInt(holder.numeroTotDegliElementi.getText().toString());
                quantitaAumenta++;
                Elemento.setQuantita(quantitaAumenta);
                holder.numeroTotDegliElementi.setText(String.valueOf(quantitaAumenta));

                arrayList.set(holder.getAdapterPosition(), Elemento);
                holder.prezzoElemento.setText(String.format(Locale.getDefault(), "%.2f €", Elemento.getCosto().multiply(BigDecimal.valueOf(quantitaAumenta))));
            });
            holder.diminuisciNumeroElementi.setOnClickListener(v -> {
                int quantaDiminuisce = Integer.parseInt(holder.numeroTotDegliElementi.getText().toString());
                quantaDiminuisce--;
                if (quantaDiminuisce == 0) {
                    popupDeleteElemento(holder, Elemento, quantaDiminuisce);
                } else {
                    arrayList.set(holder.getAdapterPosition(), Elemento);
                    holder.numeroTotDegliElementi.setText(String.valueOf(quantaDiminuisce));
                    Elemento.setQuantita(quantaDiminuisce);
                }

                holder.prezzoElemento.setText(String.format("%s €",
                        Elemento.getCosto().multiply(BigDecimal.valueOf(Elemento.getQuantita()))));

            });
        }

    }

    public List<DtoElementi> getArrayList() {
        if (Objects.isNull(arrayList)) {
            return null;
        } else {
            return arrayList;

        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderAggiungiElementoAlTavolo holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return (int) arrayList.stream().filter(dtoElemento -> dtoElemento.getQuantita() != 0).count();
    }

    public static class ViewHolderAggiungiElementoAlTavolo extends RecyclerView.ViewHolder {
        private final TextView nomeElemento;
        private final TextView prezzoElemento;
        private final TextView numeroTotDegliElementi;
        private final ImageButton diminuisciNumeroElementi;
        private final ImageButton aumentaNumeroElementi;
        private final CardView cardView;

        public ViewHolderAggiungiElementoAlTavolo(@NonNull View itemView) {
            super(itemView);
            nomeElemento = itemView.findViewById(R.id.nomeElemento);
            prezzoElemento = itemView.findViewById(R.id.prezzoElemento);
            numeroTotDegliElementi = itemView.findViewById(R.id.numeroTotDegliElementi);
            cardView = itemView.findViewById(R.id.cardViewElementoTavolo);
            diminuisciNumeroElementi = itemView.findViewById(R.id.diminuisciNumeroElemento);
            aumentaNumeroElementi = itemView.findViewById(R.id.aumentaNumeroElemento);

        }


    }

    private void popupDeleteElemento(@NonNull ViewHolderAggiungiElementoAlTavolo holder,
                                    DtoElementi elemento, int quantita) {
        AlertDialog.Builder alert = new AlertDialog.Builder(aggiungiElementoAlTavoloFragment.getContext());
        alert.setTitle("Sei sicuro di voler rimuovere dal Tavolo:");
        alert.setMessage("\"" + elemento.getElementoOrdinata().getNomeElemento() + "\"?");
        alert.setPositiveButton("Conferma", (dialog, which) -> {
            arrayList.remove(elemento);
            elemento.setQuantita(quantita);
            notifyItemRemoved(holder.getAdapterPosition());
        });
        alert.setNegativeButton("Annulla", (dialog, which) -> {
            if (aggiungiElementoAlTavoloFragment.getContext() != null) {
                Toast.makeText(alert.getContext(), "Operazione annullata", Toast.LENGTH_LONG).show();
            }
        });

        alert.setCancelable(false);
        alert.create().show();

    }
}
