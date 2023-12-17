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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneCategoriaFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneMenuFragment;
import com.INGSW2223_V_06.ratatouille23.model.Elementi;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.MenuDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;

import java.util.ArrayList;

public class MyAdapterElencoElementi extends RecyclerView.Adapter<MyAdapterElencoElementi.ViewholderElemento> {
    private static final String TAG = "MyAdapterElencoElementi";
    private Context context;
    private ArrayList<Elementi> arrayList;
    private GestioneCategoriaFragment gestioneCategoriaFragment;
    private MenuDao menuDao;


    public MyAdapterElencoElementi(ArrayList<Elementi> categories, FragmentActivity context, GestioneCategoriaFragment gestioneCategoriaFragment) {
        this.arrayList = categories;
        this.context = context;
        menuDao = new MenuDao(gestioneCategoriaFragment.getContext());
        this.gestioneCategoriaFragment = gestioneCategoriaFragment;
    }

    @NonNull
    @Override
    public ViewholderElemento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_elemento_item, parent, false);
        return new ViewholderElemento(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderElemento holder, int position) {
        Elementi elementi = arrayList.get(position);
        holder.nomeElemento.setText(String.format("Nome: %s", elementi.getNomeElemento()));
        if (elementi.getDescrizione().length() <= 15) {
            holder.descrizioneElemento.setText(String.format("Descrizione: %s ...",
                    elementi.getDescrizione()));
        } else {
            holder.descrizioneElemento.setText(String.format("Descrizione: %s ...",
                    elementi.getDescrizione().substring(0, 15)));
        }

        holder.prezzoElemento.setText(String.format("Prezzo: %s €",
                (elementi.getPrezzo())));
        holder.deleteButton.setOnClickListener(v -> popupDeleteElemento(holder, elementi));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewholderElemento extends RecyclerView.ViewHolder {
        private final TextView nomeElemento;
        private final TextView descrizioneElemento;
        private final TextView prezzoElemento;

        private final ImageButton deleteButton;

        private final CardView cardView;

        public ViewholderElemento(@NonNull View itemView) {
            super(itemView);
            nomeElemento = itemView.findViewById(R.id.nomeProdotto);
            descrizioneElemento = itemView.findViewById(R.id.descrizioneProdotto);
            prezzoElemento = itemView.findViewById(R.id.prezzoProdotto);

            deleteButton = itemView.findViewById(R.id.deleteButton);
            cardView = itemView.findViewById(R.id.CardViewElemento);

        }
    }

    private void popupDeleteElemento(@NonNull ViewholderElemento holder, Elementi elementi) {
        AlertDialog.Builder alert = new AlertDialog.Builder(gestioneCategoriaFragment.getContext());
        alert.setTitle("Sei sicuro di voler eliminare:");
        alert.setMessage("\"" + elementi.getNomeElemento() + "\"?");
        alert.setPositiveButton("Conferma", (dialog, which) -> {
            Log.i(TAG, "onClick: cancella elementi");
            menuDao.cancellaElemento(elementi.getNomeElemento(), new Callback() {
                @Override
                public void onSuccess(Object object) {
                    Log.i(TAG, "onSuccess: " + object.toString());
                    arrayList.remove(elementi);
                    notifyItemRemoved(holder.getAdapterPosition());
                    if (gestioneCategoriaFragment.getContext() != null) {

                        Toast.makeText(gestioneCategoriaFragment.getContext(), object.toString(),
                                Toast.LENGTH_LONG).show();
                        if (arrayList.isEmpty()) {
                            Toast.makeText(gestioneCategoriaFragment.getContext(), "Categoria eliminata con successo",
                                    Toast.LENGTH_LONG).show();
                            FragmentUtils.changeFragment(new GestioneMenuFragment(), gestioneCategoriaFragment);

                        }
                    }

                }

                @Override
                public void onFailure(Throwable error) {
                    Log.i(TAG, "onFailure: ");
                    if (gestioneCategoriaFragment.getContext() != null)
                        if (error.getMessage().contains("Elemento non più disponibile")) {
                            Toast.makeText(gestioneCategoriaFragment.getContext(), error.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            arrayList.remove(elementi);
                            notifyItemRemoved(holder.getAdapterPosition());

                        } else {
                            Toast.makeText(gestioneCategoriaFragment.getContext(), error.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                }
            });
        });
        alert.setNegativeButton("Annulla", (dialog, which) -> {
            if (gestioneCategoriaFragment.getContext() != null)
                Toast.makeText(alert.getContext(), "Operazione annullata", Toast.LENGTH_LONG).show();
        });

        alert.setCancelable(false);
        alert.create().show();

    }

    public ArrayList<Elementi> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Elementi> arrayList) {
        this.arrayList = arrayList;
    }


}
