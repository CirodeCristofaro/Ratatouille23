package com.INGSW2223_V_06.ratatouille23.recyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.AddettoSalaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AggiungiElementoAlTavoloFragment;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoTavoloOrdinato;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdapterElencoTavoli extends RecyclerView.Adapter<MyAdapterElencoTavoli.ViewHolderElencoTavoliOccupati> {
    private Context context;
    private ArrayList<DtoTavoloOrdinato> arrayList;
    private AddettoSalaPanelFragment addettoSalaPanelFragment;

    private final String TAG = "MyAdapterElencoTavoli";

    public MyAdapterElencoTavoli(Context context, ArrayList<DtoTavoloOrdinato> arrayList, AddettoSalaPanelFragment addettoSalaPanelFragment) {
        this.context = context;
        this.arrayList = arrayList;
        this.addettoSalaPanelFragment = addettoSalaPanelFragment;
    }

    @NonNull
    @Override
    public ViewHolderElencoTavoliOccupati onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_tavolo_item, parent, false);
        return new ViewHolderElencoTavoliOccupati(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterElencoTavoli.ViewHolderElencoTavoliOccupati holder, int position) {
        DtoTavoloOrdinato Elemento = arrayList.get(position);
        holder.numeroTavoloinSala.setText(String.format("N°tavolo: %s",
                Elemento.getNumeroTavolo()));
        holder.totaleElementoTavolo.setText(String.format("N°Elementi: %s",
                Elemento.getTotaleElementi()));
        holder.totalePrezzoTavolo.setText(String.format(Locale.getDefault(), "Totale: %.2f €",
                Elemento.getTotaleCosto()));

        holder.cardView.setOnClickListener(v -> {
                    AggiungiElementoAlTavoloFragment aggiungiElementoAlTavoloFragment = new AggiungiElementoAlTavoloFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("idTavolo",
                            String.valueOf(Elemento.getIdTavoloOrdinato()));
                    aggiungiElementoAlTavoloFragment.setArguments(bundle);
                    FragmentUtils.changeFragment(aggiungiElementoAlTavoloFragment, addettoSalaPanelFragment);

                }
        );


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolderElencoTavoliOccupati extends RecyclerView.ViewHolder {
        private final TextView numeroTavoloinSala;
        private final TextView totaleElementoTavolo;
        private final TextView totalePrezzoTavolo;
        private final CardView cardView;

        public ViewHolderElencoTavoliOccupati(@NonNull View itemView) {
            super(itemView);
            numeroTavoloinSala = itemView.findViewById(R.id.numeroTavoloinSala);
            totaleElementoTavolo = itemView.findViewById(R.id.totaleElementiTavolo);
            totalePrezzoTavolo = itemView.findViewById(R.id.totalePrezzoTavolo);
            cardView = itemView.findViewById(R.id.cardViewTavolo);

        }
    }
}
