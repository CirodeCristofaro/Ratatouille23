package com.INGSW2223_V_06.ratatouille23.recyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneCategoriaFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneMenuFragment;
import com.INGSW2223_V_06.ratatouille23.model.Categorie;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;

import java.util.ArrayList;

public class MyAdapterElencoCategorie extends RecyclerView.Adapter<MyAdapterElencoCategorie.Viewholder> {
    private final Context context;
    private final ArrayList<Categorie> arrayList;
    private final GestioneMenuFragment gestioneMenuFragment;


    public MyAdapterElencoCategorie(ArrayList<Categorie> categories, FragmentActivity context, GestioneMenuFragment gestioneMenuFragment) {
        this.arrayList = categories;
        this.context = context;
        this.gestioneMenuFragment = gestioneMenuFragment;

    }


    @NonNull
    @Override
    public MyAdapterElencoCategorie.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_categoria_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterElencoCategorie.Viewholder holder, int position) {
        Categorie categorie = arrayList.get(position);
        holder.titleText.setText(categorie.getNomeCategoria());


        holder.cardView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("categoria", categorie.getNomeCategoria());
            GestioneCategoriaFragment gestioneCategoriaFragment = new GestioneCategoriaFragment();
            gestioneCategoriaFragment.setArguments(bundle);
            FragmentUtils.changeFragment(gestioneCategoriaFragment, gestioneMenuFragment);
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView titleText;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewElencoCategoria);
            titleText = itemView.findViewById(R.id.tital_text);

        }
    }
}
