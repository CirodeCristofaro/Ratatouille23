package com.INGSW2223_V_06.ratatouille23.recyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.ElencoAvvisiFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.VisualizzaAvvisoFragment;
import com.INGSW2223_V_06.ratatouille23.model.Avviso;
import com.INGSW2223_V_06.ratatouille23.model.TipoVisualizzazione;
import com.INGSW2223_V_06.ratatouille23.model.VisualizzaAvviso;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.AvvisoDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MyAdapterElencoAvvisi extends RecyclerView.Adapter<MyAdapterElencoAvvisi.ViewHolder> {
    private final String TAG = "MyAdapterElencoAvvisi";
    private final ArrayList<VisualizzaAvviso> modelAvvisoList;
    private final Context context;
    private final ElencoAvvisiFragment elencoAvvisiFragment;
    private final AvvisoDao avvisoDao;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public MyAdapterElencoAvvisi(ArrayList<VisualizzaAvviso> modelAvvisoList, FragmentActivity context,
                                 ElencoAvvisiFragment elencoAvvisiFragment) {
        this.modelAvvisoList = modelAvvisoList;
        this.context = context;
        this.elencoAvvisiFragment = elencoAvvisiFragment;
        avvisoDao = new AvvisoDao(elencoAvvisiFragment.getContext());
    }

    @NonNull
    @Override
    public MyAdapterElencoAvvisi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_single_view_avviso, parent, false);

        return new ViewHolder(view, listener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapterElencoAvvisi.ViewHolder holder, int position) {
        VisualizzaAvviso visualizzaAvviso = modelAvvisoList.get(position);
        Avviso avviso = visualizzaAvviso.getAvvisoRicevuto();
        holder.OggettoTxt.setText(String.format("Oggetto: %s", avviso.getOggetto()));
        if (avviso.getContenuto().length() >= 10) {
            holder.ContenutoTxt.setText(String.format("Contenuto: %s...", avviso.getContenuto().substring(0, 10)));
        } else {
            holder.ContenutoTxt.setText(String.format("Contenuto: %s...", avviso.getContenuto()));
        }
        if (avviso.getOggetto().length() >= 10) {
            holder.OggettoTxt.setText(String.format("Oggetto: %s...",
                    avviso.getOggetto().substring(0, 10)));
        } else {
            holder.OggettoTxt.setText(String.format("Oggetto: %s...", avviso.getOggetto()));
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ITALY);
        try {
            Date data = formatter.parse(avviso.getData());
            formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY);
            assert data != null;
            String dataFormattata = formatter.format(data);
            holder.DataTxt.setText(String.format("Data: %s", dataFormattata));
        } catch (ParseException e) {
            holder.DataTxt.setText("null");
        }

        holder.cardView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("avvisoDaVisualizzare", avviso);
            VisualizzaAvvisoFragment visualizza = new VisualizzaAvvisoFragment();
            visualizza.setArguments(bundle);
            avvisoDao.cambiaStatoAvviso(
                    avviso.getIdAvviso(), TipoVisualizzazione.visualizzato, new Callback() {
                        @Override
                        public void onSuccess(Object object) {
                            Log.i(TAG, object.toString());
                        }

                        @Override
                        public void onFailure(Throwable error) {
                            Log.e(TAG, "onFailure: ", error);
                            if (elencoAvvisiFragment.getContext() != null)
                                Toast.makeText(elencoAvvisiFragment.getContext(), error.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                        }
                    });
            FragmentUtils.changeFragment(visualizza, elencoAvvisiFragment);
        });

        holder.visualizzaText.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("avvisoDaVisualizzare", avviso);
            VisualizzaAvvisoFragment visualizza = new VisualizzaAvvisoFragment();
            visualizza.setArguments(bundle);
            avvisoDao.cambiaStatoAvviso(
                    avviso.getIdAvviso(), TipoVisualizzazione.visualizzato, new Callback() {
                        @Override
                        public void onSuccess(Object object) {
                            Log.i(TAG, object.toString());
                        }

                        @Override
                        public void onFailure(Throwable error) {
                            Log.e(TAG, "onFailure: ", error);
                            if (elencoAvvisiFragment.getContext() != null)
                                Toast.makeText(elencoAvvisiFragment.getContext(), error.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                        }
                    });
            FragmentUtils.changeFragment(visualizza, elencoAvvisiFragment);
        });

        holder.nascondiTxt.setOnClickListener(view -> {
            listener.onItemClick(holder.getLayoutPosition());
            avvisoDao.cambiaStatoAvviso(avviso.getIdAvviso(), TipoVisualizzazione.nascosto, new Callback() {
                @Override
                public void onSuccess(Object object) {
                    Log.i("Avviso ", object.toString());
                    if (getItemCount() == 0) {
                        Log.i("count", String.valueOf(getItemCount()));
                        FragmentUtils.changeFragment(new ElencoAvvisiFragment(), elencoAvvisiFragment);
                    }
                }

                @Override
                public void onFailure(Throwable error) {
                    if (elencoAvvisiFragment.getContext() != null)
                        Toast.makeText(elencoAvvisiFragment.getContext(), error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                }
            });
        });

        if (Objects.nonNull(visualizzaAvviso.getVisualizzazione()) &&
                visualizzaAvviso.getVisualizzazione().equals(TipoVisualizzazione.visualizzato)) {
            holder.occhioVisualizzazione.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return modelAvvisoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView OggettoTxt;
        private final TextView ContenutoTxt;
        private final TextView DataTxt;
        private final TextView visualizzaText;
        private final TextView nascondiTxt;
        private final CardView cardView;

        private final ImageView occhioVisualizzazione;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            OggettoTxt = itemView.findViewById(R.id.oggettoTxt);
            ContenutoTxt = itemView.findViewById(R.id.contenutoTxt);
            DataTxt = itemView.findViewById(R.id.dataTxt);
            cardView = itemView.findViewById(R.id.CardViewElencoAvviso);
            visualizzaText = itemView.findViewById(R.id.visualizzaButton);
            nascondiTxt = itemView.findViewById(R.id.nascondiButton);
            occhioVisualizzazione = itemView.findViewById(R.id.occhioVisualizzato);
        }
    }
}
